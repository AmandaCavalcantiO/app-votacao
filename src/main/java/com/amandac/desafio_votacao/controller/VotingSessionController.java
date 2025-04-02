package com.amandac.desafio_votacao.controller;

import com.amandac.desafio_votacao.dto.request.OpenVotingSessionDTO;
import com.amandac.desafio_votacao.dto.request.RegisterVoteDTO;
import com.amandac.desafio_votacao.entity.VotingSession;
import com.amandac.desafio_votacao.service.VoteService;
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


@Tag(name = "Sessão de votação", description = "Gerenciamento das sessões de votação")
@Slf4j
@RestController
@RequestMapping("api/v1/voting/sessions")
public class VotingSessionController {

    private final VotingSessionService votingSessionService;
    private final VoteService voteService;

    public VotingSessionController(VotingSessionService votingSessionService, VoteService voteService) {
        this.votingSessionService = votingSessionService;
        this.voteService = voteService;
    }

    @Operation(summary = "Abertura de uma sessão de votação para uma pauta")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Criação da sessão de votação bem sucedida.")})
    @RequestMapping(value = "/open", method =  RequestMethod.POST, produces="application/json", consumes="application/json")
    public ResponseEntity<VotingSession> save(@Valid @RequestBody OpenVotingSessionDTO openVotingSessionDTO){
        log.info("Requisicao para abrir sessao de votacao da pauta {} recebida", openVotingSessionDTO.getIdTopic() );

        return new ResponseEntity<>(votingSessionService.openVotingSession(openVotingSessionDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Lista as sessões de votação abertas")
    @RequestMapping(value = "/open", method =  RequestMethod.GET, produces="application/json")
    public ResponseEntity<Page<VotingSession>> getOpenVotingSessions(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                     @RequestParam(value = "size", defaultValue = "10") Integer size){
        log.info("Requisicao para listar todas as sessoes de votacao abertas recebida");

        return ResponseEntity.ok().body(votingSessionService.getOpenSessions(page, size));
    }

    @Operation(summary = "Fecha a sessão de votação antes do tempo indicado na criação da sessão")
    @PostMapping("/close/{id}")
    public ResponseEntity<VotingSession> close(@PathVariable String id){
        log.info("Requisicao para encerramento da sessao {} recebida", id);

        return new ResponseEntity<VotingSession>(votingSessionService.closeVotingSession(id), HttpStatus.OK);
    }

    @Operation(summary = "Registra o voto de um associado na sessão indicada")
    @RequestMapping(value = "/vote", method =  RequestMethod.POST, produces="application/json", consumes="application/json")
    public ResponseEntity<String> registerVote(@Valid @RequestBody RegisterVoteDTO registerVoteDTO) {
        log.info("Recebendo voto para sessao {}", registerVoteDTO.getIdVotingSession());

        voteService.registerVote(registerVoteDTO);
        return ResponseEntity.ok("Voto registrado");
    }
}