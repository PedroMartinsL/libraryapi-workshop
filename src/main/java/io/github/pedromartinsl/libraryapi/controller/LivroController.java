package io.github.pedromartinsl.libraryapi.controller;

import java.net.URI;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.pedromartinsl.libraryapi.controller.common.mappers.LivroMapper;
import io.github.pedromartinsl.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.pedromartinsl.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.pedromartinsl.libraryapi.model.GeneroLivro;
import io.github.pedromartinsl.libraryapi.model.Livro;
import io.github.pedromartinsl.libraryapi.service.LivroService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = mapper.toEntity(dto);
        service.salvar(livro);

        URI url = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable String id) {
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    ResultadoPesquisaLivroDTO dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable String id) {
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    service.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String titulo,
            @RequestParam(value = "nome_autor", required = false) String nomeAutor,
            @RequestParam(required = false) GeneroLivro genero,
            @RequestParam(value = "ano-publicacao", required = false) Integer anoPublicacao,
            @RequestParam(defaultValue = "0") Integer pagina,
            @RequestParam(defaultValue = "10") Integer tamanhoPagina) {

        Page<Livro> paginaResultado = service.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);

        Page<ResultadoPesquisaLivroDTO> resultado = paginaResultado.map(mapper::toDTO);

        /*Before Page 
         * var lista = resultado.stream().map(mapper::toDTO).collect(Collectors.toList());
        */
        

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable String id,
            @RequestBody @Valid CadastroLivroDTO dto) {
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    Livro entidadeAux = mapper.toEntity(dto);

                    livro.setDataPublicacao(entidadeAux.getDataPublicacao());
                    livro.setIsbn(entidadeAux.getIsbn());
                    livro.setPreco(entidadeAux.getPreco());
                    livro.setGenero(entidadeAux.getGenero());
                    livro.setTitulo(entidadeAux.getTitulo());
                    livro.setAutor(entidadeAux.getAutor());

                    service.atualizar(livro);

                    return ResponseEntity.noContent().build();

                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
