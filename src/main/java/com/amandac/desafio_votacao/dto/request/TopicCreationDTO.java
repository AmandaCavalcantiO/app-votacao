package com.amandac.desafio_votacao.dto.request;

import com.amandac.desafio_votacao.entity.Topic;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TopicCreationDTO {
    @Schema(description = "Título da pauta a ser criada.", example = "Criação de áreas Pet-Friendly")
    @NotBlank(message = "Título da pauta não pode ser vazio")
    private String title;

    @Schema(description = "Descrição da pauta a ser criada.", example = " Proposta para a criação de espaços para animais de estimação dentro das instalações")
    @NotBlank(message = "Descrição da pauta não pode ser vazia")
    private String description;

    public Topic toEntity() {
        Topic topic = new Topic();
        topic.setTitle(this.title);
        topic.setDescription(this.description);
        return topic;
    }
}
