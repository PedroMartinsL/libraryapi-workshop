package io.github.pedromartinsl.libraryapi.controller.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record UsuarioDTO(
        @NotBlank(message = "campo obrigatório") 
        String login, 
        @NotBlank(message = "campo obrigatório") 
        String senha, 
        @Email (message = "invalido") 
        @NotBlank(message = "campo obrigatório") 
        String email, 
        List<String> roles) {
}
