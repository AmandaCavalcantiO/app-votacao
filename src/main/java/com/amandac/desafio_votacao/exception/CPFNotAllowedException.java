package com.amandac.desafio_votacao.exception;

public class CPFNotAllowedException extends RuntimeException {

    public CPFNotAllowedException(String message) {
        super(message);
    }
}