package com.amandac.desafio_votacao.service;

import com.amandac.desafio_votacao.dto.request.OpenVotingSessionDTO;
import com.amandac.desafio_votacao.dto.request.RegisterVoteDTO;
import com.amandac.desafio_votacao.dto.response.VotingResultDTO;
import com.amandac.desafio_votacao.entity.Topic;
import com.amandac.desafio_votacao.entity.VotingSession;
import com.amandac.desafio_votacao.enums.VoteOption;
import com.amandac.desafio_votacao.exception.ResourceNotFoundException;
import com.amandac.desafio_votacao.exception.VotingSessionAlreadyClosedException;
import com.amandac.desafio_votacao.exception.VotingSessionAlreadyOpenException;
import com.amandac.desafio_votacao.repository.VoteRepository;
import com.amandac.desafio_votacao.repository.VotingSessionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VotingSessionService {

    @Value("${voting.session.default.duration}")
    private int defaultDuration;

    private final TopicService topicService;
    private final VotingSessionRepository votingSessionRepository;
    private final VoteRepository voteRepository;


    public VotingSessionService(TopicService topicService, VotingSessionRepository votingSessionRepository, VoteRepository voteRepository) {
        this.topicService = topicService;
        this.votingSessionRepository = votingSessionRepository;
        this.voteRepository = voteRepository;
    }

    public VotingSession findById(String id){
        return votingSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sessão de votação não encontrada"));
    }

    public VotingSession findFirstByIdTopic(String id){
        return votingSessionRepository.findFirstByIdTopicOrderByClosingDate(id, Sort.by(Sort.Direction.DESC, "closingDate"))
                .orElseThrow(() -> new IllegalArgumentException("Nenhuma sessão de votação encontrada para essa pauta."));
    }

    public Page<VotingSession> getOpenSessions(Integer page, Integer size){
        LocalDateTime dateTimeNow = LocalDateTime.now();
        PageRequest pageRequest = PageRequest.of(page, size);
        return votingSessionRepository.findOpenSessions(dateTimeNow, pageRequest);
    }

    public VotingSession openVotingSession(OpenVotingSessionDTO openVotingSessionDTO) {
        Topic topic = topicService.findById(openVotingSessionDTO.getIdTopic());

        if (hasOpenSession(topic.getId())) {
            throw new VotingSessionAlreadyOpenException("Já existe uma sessão de votação aberta para esta pauta.");
        }

        VotingSession session = new VotingSession();
        session.setIdTopic(topic.getId());

        LocalDateTime dateTimeNow = LocalDateTime.now();
        session.setOpeningDate(dateTimeNow);

        int duration = (openVotingSessionDTO.getDuration() == null || openVotingSessionDTO.getDuration() < 1)
                ? defaultDuration
                : openVotingSessionDTO.getDuration();

        session.setClosingDate(dateTimeNow.plusMinutes(duration));
        return votingSessionRepository.save(session);

    }

    public VotingSession closeVotingSession(String id){
       VotingSession votingSession = this.findById(id);

        if (!votingSession.isOpen()) {
            throw new VotingSessionAlreadyClosedException("A sessão de votação já foi encerrada.");
        }

        votingSession.setClosingDate(LocalDateTime.now());

        return votingSessionRepository.save(votingSession);
    }


    public Page<VotingResultDTO> getHistoryVotingResultsByTopic(String idTopic, int page, int size){
        Topic topic = topicService.findById(idTopic);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "endTime"));

        Page<VotingSession> votingSessions = votingSessionRepository.findClosedSessionsByIdTopic(idTopic, LocalDateTime.now(), pageable);

        return votingSessions.map(session -> {
            long totalYes = voteRepository.countByIdVotingSessionAndVoteOption(session.getId(), VoteOption.SIM);
            long totalNo = voteRepository.countByIdVotingSessionAndVoteOption(session.getId(), VoteOption.NAO);

            return new VotingResultDTO(topic.getTitle(), session.getClosingDate(), totalYes, totalNo);
        });
    }

    public VotingResultDTO getVotingResultsByTopic(String idTopic){

        Topic topic = topicService.findById(idTopic);
        VotingSession votingSession = this.findFirstByIdTopic(idTopic);

        if(votingSession.isOpen()){
            throw new IllegalStateException("A sessão de votação ainda está em andamento.");
        }

        long totalYes = voteRepository.countByIdVotingSessionAndVoteOption(votingSession.getId(), VoteOption.SIM);
        long totalNo = voteRepository.countByIdVotingSessionAndVoteOption(votingSession.getId(), VoteOption.NAO);

        return new VotingResultDTO(topic.getTitle(), votingSession.getClosingDate(), totalYes, totalNo);

    }

    private boolean hasOpenSession(String idTopic) {
        List<VotingSession> votingSessionList = votingSessionRepository.findByIdTopic(idTopic);

        return votingSessionList.stream().anyMatch(VotingSession::isOpen);
    }

}