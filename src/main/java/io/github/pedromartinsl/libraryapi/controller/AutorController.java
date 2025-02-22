package io.github.pedromartinsl.libraryapi.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.pedromartinsl.libraryapi.controller.common.mappers.AutorMapper;
import io.github.pedromartinsl.libraryapi.controller.dto.AutorDTO;
import io.github.pedromartinsl.libraryapi.model.Autor;
import io.github.pedromartinsl.libraryapi.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
@Tag(name = "Autores")
@Slf4j
public class AutorController implements GenericController {

    private final AutorService service;
    private final AutorMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(
        summary = "Salvar",
        description = "Cadastrar novo autor"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
        @ApiResponse(responseCode = "422", description = "Erro de validação"),
        @ApiResponse(responseCode = "409", description = "Autor já cadastrado")
    })
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto) {
        log.info("Cadastrando novo autor: {}", dto.nome());

        Autor autor = mapper.toEntity(dto);
        service.salvar(autor);
        URI location = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable String id) {
        UUID idAutor = UUID.fromString(id);
        return service.obterPorId(idAutor).map(autor -> {
            AutorDTO dto = mapper.toDTO(autor);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        log.info("Deletando autor de ID: {} ", id);
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(required = false) String nome,
            @RequestParam(required = false) String nacionalidade) {
        List<Autor> lista = service.pesquisa(nome, nacionalidade);
        List<AutorDTO> listaDTO = lista.stream().map(mapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(listaDTO);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> atualizar(@PathVariable String id, @RequestBody @Valid AutorDTO dto) {

        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Autor autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        service.atualizar(autor);

        return ResponseEntity.noContent().build();

    }
}