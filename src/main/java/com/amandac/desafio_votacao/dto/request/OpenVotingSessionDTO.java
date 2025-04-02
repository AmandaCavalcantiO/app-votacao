package com.amandac.desafio_votacao.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OpenVotingSessionDTO {
    @Schema(description = "Id da pauta para a qual a sessão de votação será aberta.", example = "67ec6b599637fd5a6b16e0fc")
    @NotBlank(message = "A sessão de votação é obrigatório")
    private String idTopic;

    @Schema(description = "Tempo em que a sessão de votacão ficará aberta em minutos inteiros", example = "5")
    private Integer duration;
}
