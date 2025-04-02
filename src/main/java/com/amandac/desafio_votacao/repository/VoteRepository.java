package com.amandac.desafio_votacao.repository;

import com.amandac.desafio_votacao.entity.Vote;
import com.amandac.desafio_votacao.enums.VoteOption;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends MongoRepository<Vote, String> {

    Vote findByIdVotingSessionAndMemberDocument(String idVotingSession, String memberDocument);

    Boolean existsByIdVotingSessionAndMemberDocument(String idVotingSession, String memberDocument);

    @Query(value = "{ 'idVotingSession' : ?0, 'option' : ?1 }", count = true)
    Long countByIdVotingSessionAndVoteOption(String idVotingSession, VoteOption option);
}
