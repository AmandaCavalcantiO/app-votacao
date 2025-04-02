package com.amandac.desafio_votacao.exception;

public class VotingSessionAlreadyClosedException extends RuntimeException {

    public VotingSessionAlreadyClosedException(String message) {
        super(message);
    }
}