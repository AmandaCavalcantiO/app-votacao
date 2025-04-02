package com.amandac.desafio_votacao.entity;

import com.amandac.desafio_votacao.enums.VoteOption;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "vote")
public class Vote {
    @Id
    private String id;
    private String idVotingSession;
    private String memberDocument;
    private VoteOption option;
}
