package com.rafaelsousa.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDTO {

    @NotBlank(message = "Título não pode ser vazio ou nulo")
    @Size(min = 3, max = 100, message = "Título deve ter no mínimo {min} e no máximo {max} caracteres")
    private String title;

    @NotBlank(message = "Autor não pode ser vazio ou nulo")
    @Size(min = 3, max = 20, message = "Author deve ter no mínimo {min} e no máximo {max} caracteres")
    private String author;

    @NotBlank(message = "ISBN não pode ser vazio ou nulo")
    private String ISBN;

}
