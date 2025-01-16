package io.github.pedromartinsl.libraryapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.ISBN;

import io.github.pedromartinsl.libraryapi.model.GeneroLivro;

public record CadastroLivroDTO(
    @ISBN
    @NotBlank(message = "campo obrigatório")
    String isbn, 
    @NotBlank(message = "campo obrigatório")
    String titulo,
    @NotNull(message = "campo obrigatório")
    @Past(message = "nao pode ser uma data futura")
    LocalDate dataPublicacao,
    GeneroLivro genero,
    BigDecimal preco,
    @NotNull(message = "campo obrigatório")
    UUID idAutor
) {

}
