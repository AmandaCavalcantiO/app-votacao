package com.amandac.desafio_votacao.dto.request;

import com.amandac.desafio_votacao.enums.VoteOption;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterVoteDTO {

    @Schema(description = "Id da sessão de votação para a qual se está registrando o voto.", example = "67ec6b599637fd5a6b16e0fc")
    @NotBlank(message = "A sessão de votação é obrigatório")
    String idVotingSession;

    @Schema(description = "Cpf do associado que registrando o voto. Somente números", example = "45668478025")
    @NotBlank(message = "O cpf do associado é obrigatório")
    String memberDocument;

    @Schema(description = "Opção selecionada. Somente opção SIM ou NAO.", example = "SIM")
    @NotNull(message = "A opção de voto é obrigatória")
    VoteOption option;
}
