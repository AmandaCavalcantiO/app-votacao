package com.amandac.desafio_votacao.service;

import com.amandac.desafio_votacao.client.CPFvalidationClient;
import com.amandac.desafio_votacao.dto.request.RegisterVoteDTO;
import com.amandac.desafio_votacao.entity.Vote;
import com.amandac.desafio_votacao.entity.VotingSession;
import com.amandac.desafio_votacao.enums.CPFValidationStatus;
import com.amandac.desafio_votacao.exception.AlreadyVotedException;
import com.amandac.desafio_votacao.exception.CPFNotAllowedException;
import com.amandac.desafio_votacao.exception.VotingSessionAlreadyClosedException;
import com.amandac.desafio_votacao.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VoteService {
    private final VotingSessionService votingSessionService;
    private final CPFvalidationClient cpfValidationClient;
    private final VoteRepository voteRepository;

    public VoteService(VotingSessionService votingSessionService, CPFvalidationClient cpFvalidationClient, VoteRepository voteRepository) {
        this.votingSessionService = votingSessionService;
        this.cpfValidationClient = cpFvalidationClient;
        this.voteRepository = voteRepository;
    }

    @Transactional
    public Vote registerVote(RegisterVoteDTO registerVoteDTO){
        VotingSession votingSession = votingSessionService.findById(registerVoteDTO.getIdVotingSession());

        if (!votingSession.isOpen()) {
            throw new VotingSessionAlreadyClosedException("A sessão de votação já foi encerrada.");
        }

        CPFValidationStatus cpfStatus = cpfValidationClient.validateCPF(registerVoteDTO.getMemberDocument());

        if (cpfStatus.equals(CPFValidationStatus.UNABLE_TO_VOTE)){
            throw new CPFNotAllowedException("O associado não está autorizado a votar.");
        }

        Boolean hasVoted = voteRepository.existsByIdVotingSessionAndMemberDocument(
                registerVoteDTO.getIdVotingSession(), registerVoteDTO.getMemberDocument());

        if(hasVoted) throw new
                AlreadyVotedException("Associado já votou nessa sessão de votação.");

        Vote vote = new Vote();
        vote.setIdVotingSession(registerVoteDTO.getIdVotingSession());
        vote.setMemberDocument(registerVoteDTO.getMemberDocument());
        vote.setOption(registerVoteDTO.getOption());

        return voteRepository.save(vote);
    }
}
