package io.github.pedromartinsl.libraryapi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Autor")
public record AutorDTO(
        UUID id,
        @NotBlank(message = "campo obrigatório")
        @Size(min = 2, max = 100, message = "Campo fora do tamanho padrão")
        @Schema(name = "nome")
        String nome, 
        @NotNull(message = "campo obrigatório")
        @Past(message = "não pode ser uma data futura")
        @Schema(name = "dataNascimento")
        LocalDate dataNascimento, 
        @NotBlank(message = "campo obrigatório")
        @Size(min = 2, max = 50, message = "Campo fora do tamanho padrão")
        @Schema(name = "nacionalidade")
        String nacionalidade) {
    //anotações tratadas no handler - fe.getField(), fe.getDefaultMessage()
}
