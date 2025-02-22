package io.github.pedromartinsl.libraryapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import io.github.pedromartinsl.libraryapi.model.GeneroLivro;

public record ResultadoPesquisaLivroDTO(
    UUID id,
    String isbn, 
    String titulo,
    LocalDate dataPublicacao,
    GeneroLivro genero,
    BigDecimal preco,
    AutorDTO autor
    ) {

}
