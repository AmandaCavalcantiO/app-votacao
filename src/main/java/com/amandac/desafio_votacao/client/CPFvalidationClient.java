package com.amandac.desafio_votacao.client;

import com.amandac.desafio_votacao.enums.CPFValidationStatus;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CPFvalidationClient {

    private final Random random = new Random();

    public CPFValidationStatus validateCPF(String cpf) {
        if (!isValidFormat(cpf)) {
            throw new IllegalArgumentException("CPF inválido");
        }

        return random.nextBoolean() ? CPFValidationStatus.ABLE_TO_VOTE : CPFValidationStatus.UNABLE_TO_VOTE;
    }

    private boolean isValidFormat(String cpf) {
        return cpf != null && cpf.matches("\\d{11}");
    }
}
