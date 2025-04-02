package com.amandac.desafio_votacao.service;

import com.amandac.desafio_votacao.dto.request.OpenVotingSessionDTO;
import com.amandac.desafio_votacao.entity.Topic;
import com.amandac.desafio_votacao.entity.VotingSession;
import com.amandac.desafio_votacao.repository.VoteRepository;
import com.amandac.desafio_votacao.repository.VotingSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VotingSessionServiceTest {
    @Mock
    private TopicService topicService;
    @Mock
    private VotingSessionRepository votingSessionRepository;
    @Mock
    private VoteRepository voteRepository;
    @InjectMocks
    private  VotingSessionService votingSessionService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should open voting session successfully")
    void openVotingSession() {
        Topic topic = new Topic();
        topic.setId("67ed9a33ac98d035a2408ae5");
        topic.setTitle("Regulamentação das áreas Pet-Friendly");
        topic.setDescription("Debate sobre a criação de regras claras para convivência entre tutores de pets e demais moradores para uso de áreas comuns e regras de higiene.");

        when(topicService.findById("67ed9a33ac98d035a2408ae5")).thenReturn(topic);

        OpenVotingSessionDTO openVotingSessionDTO = new OpenVotingSessionDTO();
        openVotingSessionDTO.setIdTopic("67ed9a33ac98d035a2408ae5");

        votingSessionService.openVotingSession(openVotingSessionDTO);

        verify(votingSessionRepository, times(2)).save(any());
        ArgumentCaptor<VotingSession> captor = ArgumentCaptor.forClass(VotingSession.class);
        verify(votingSessionRepository, times(1)).save(captor.capture());

        VotingSession capturedSession = captor.getValue();
        assertNotNull(capturedSession);
        assertEquals("67ed9a33ac98d035a2408ae5", capturedSession.getIdTopic());
        assertEquals(capturedSession.getOpeningDate().plusMinutes(1),capturedSession.getClosingDate() );
    }
}