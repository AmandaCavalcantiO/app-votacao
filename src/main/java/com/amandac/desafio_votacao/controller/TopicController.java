package com.amandac.desafio_votacao.controller;

import com.amandac.desafio_votacao.dto.request.TopicCreationDTO;
import com.amandac.desafio_votacao.dto.response.VotingResultDTO;
import com.amandac.desafio_votacao.entity.Topic;
import com.amandac.desafio_votacao.service.TopicService;
import com.amandac.desafio_votacao.service.VotingSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Pautas", description = "Gerenciamento de pautas")
@Slf4j
@RestController
@RequestMapping("api/v1/topics")
public class TopicController {

    private final TopicService topicService;
    private final VotingSessionService votingSessionService;

    public TopicController(TopicService topicService, VotingSessionService votingSessionService) {
        this.topicService = topicService;
        this.votingSessionService = votingSessionService;
    }
    @Operation(summary = "Listar todas as pautas paginada")
    @RequestMapping(method =  RequestMethod.GET, produces="application/json")
    public ResponseEntity<Page<Topic>> getAllTopics(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(value = "size", defaultValue = "10") Integer size){
        log.info("Requisicao para listar pautas recebida.");

        return ResponseEntity.ok().body(topicService.getAll(page, size));
    }

    @Operation(summary = "Criação de nova pauta")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Criação da pauta bem sucedida.")})
    @RequestMapping(value = "/create", method =  RequestMethod.POST, produces="application/json", consumes="application/json")
    public ResponseEntity<Topic> save(@Valid @RequestBody TopicCreationDTO topicCreationDTO){
        log.info("Requisicao para criacao de nova pauta recebida.");

        return new ResponseEntity<>(topicService.createTopic(topicCreationDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Buscar resultado da votação por pauta")
    @RequestMapping(value= "/result/{id}", method =  RequestMethod.GET, produces="application/json")
    public ResponseEntity<VotingResultDTO> getVotingResultsByTopic(@PathVariable String id){
        log.info("Requisicao para buscar resultado da votação da pauta com id: {} recebida", id);

        return new ResponseEntity<>(votingSessionService.getVotingResultsByTopic(id), HttpStatus.OK);
    }

    @Operation(summary = "Listar histórico de votações da pauta paginado")
    @RequestMapping(value= "/history/{id}", method =  RequestMethod.GET, produces="application/json")
    public ResponseEntity<Page<VotingResultDTO>> getHistoryResultsByTopic(@PathVariable String id,
                                                                          @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                          @RequestParam(value = "size", defaultValue = "10") Integer size){
        log.info("Requisicao para listar resultados de votacoes da pauta com id: {} recebida", id);

        return ResponseEntity.ok().body(votingSessionService.getHistoryVotingResultsByTopic(id, page, size));
    }


}
