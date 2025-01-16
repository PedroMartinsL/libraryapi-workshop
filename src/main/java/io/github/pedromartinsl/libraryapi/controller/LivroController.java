package io.github.pedromartinsl.libraryapi.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.pedromartinsl.libraryapi.controller.common.mappers.LivroMapper;
import io.github.pedromartinsl.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.pedromartinsl.libraryapi.controller.dto.ErroResposta;
import io.github.pedromartinsl.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.pedromartinsl.libraryapi.model.Livro;
import io.github.pedromartinsl.libraryapi.service.LivroService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        try {
            Livro livro = mapper.toEntity(dto);
            service.salvar(livro);
            return ResponseEntity.ok(livro);
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.confito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
