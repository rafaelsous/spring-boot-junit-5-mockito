package com.rafaelsousa.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ValidationMessage {

    PARA_CONSULTAR_DEVE_SER_INFORMADO_O_ISBN_DO_LIVRO("Para consultar deve ser informado o número ISBN");

    private final String description;

}
