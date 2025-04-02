package com.amandac.desafio_votacao.exception;

public class VotingSessionAlreadyOpenException extends RuntimeException {

    public VotingSessionAlreadyOpenException(String message) {
        super(message);
    }
}