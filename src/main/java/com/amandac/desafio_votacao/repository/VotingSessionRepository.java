package com.amandac.desafio_votacao.repository;

import com.amandac.desafio_votacao.entity.VotingSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VotingSessionRepository extends MongoRepository<VotingSession,String>
{
    List<VotingSession> findByIdTopic(String idTopic);

    @Query("{'closingDate': { $gt: ?0 } }")
    Page<VotingSession> findOpenSessions(LocalDateTime dateTimeNow, Pageable pageable);

    Optional<VotingSession> findFirstByIdTopicOrderByClosingDate(String idTopic, Sort sort);

    Boolean existsByIdTopic(String idTopic);

    @Query("{'idTopic' : ?0, 'endTime': { $lt: ?1 } }")
    Page<VotingSession> findClosedSessionsByIdTopic(String idTopic, LocalDateTime dateTimeNow, Pageable pageable);
}
