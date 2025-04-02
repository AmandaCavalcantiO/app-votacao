package com.amandac.desafio_votacao.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jdk.javadoc.doclet.Doclet;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API de votações em pautas do cooperativismo", version = "v1"))
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi topicsGroup() {
        return GroupedOpenApi.builder()
                .group("topics")
                .displayName("Pautas")
                .pathsToMatch("/api/v1/topics/**")
                .build();
    }

    @Bean
    public GroupedOpenApi votingSessionGroup() {
        return GroupedOpenApi.builder()
                .group("sessions")
                .displayName("Sessão de votação")
                .pathsToMatch("/api/v1/voting/**")
                .build();
    }
}

