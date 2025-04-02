package com.amandac.desafio_votacao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "voting_session")
public class VotingSession {
    @Id
    private String id;
    private String idTopic;
    private LocalDateTime openingDate;
    private LocalDateTime closingDate;

    public boolean isOpen() {
        return this.closingDate.isAfter(LocalDateTime.now());
    }
}
