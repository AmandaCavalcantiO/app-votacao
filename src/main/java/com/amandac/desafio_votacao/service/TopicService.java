package com.amandac.desafio_votacao.service;

import com.amandac.desafio_votacao.dto.request.TopicCreationDTO;
import com.amandac.desafio_votacao.dto.response.VotingResultDTO;
import com.amandac.desafio_votacao.entity.Topic;
import com.amandac.desafio_votacao.entity.VotingSession;
import com.amandac.desafio_votacao.enums.VoteOption;
import com.amandac.desafio_votacao.exception.ResourceNotFoundException;
import com.amandac.desafio_votacao.repository.TopicRepository;
import com.amandac.desafio_votacao.repository.VoteRepository;
import com.amandac.desafio_votacao.repository.VotingSessionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;

    }

    public Topic createTopic(TopicCreationDTO topicCreationDTO){
       return this.topicRepository.save(topicCreationDTO.toEntity());
    }

    public Page<Topic> getAll(Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page, size);

        return topicRepository.findAll(pageRequest);
    }

    public Topic findById(String id){
        return topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pauta não encontrada"));
    }


}
