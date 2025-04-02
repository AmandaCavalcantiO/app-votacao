package com.amandac.desafio_votacao.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VotingResultDTO {
    private String topicTitle;
    private LocalDateTime closingTime;
    private Long totalYesVotes;
    private Long totalNoVotes;
}
