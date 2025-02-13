package io.github.pedromartinsl.libraryapi.service;

import static io.github.pedromartinsl.libraryapi.repository.specs.LivroSpecs.anoPublicacaoEqual;
import static io.github.pedromartinsl.libraryapi.repository.specs.LivroSpecs.generoEqual;
import static io.github.pedromartinsl.libraryapi.repository.specs.LivroSpecs.isbnEqual;
import static io.github.pedromartinsl.libraryapi.repository.specs.LivroSpecs.nomeAutorLike;
import static io.github.pedromartinsl.libraryapi.repository.specs.LivroSpecs.tituloLike;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.github.pedromartinsl.libraryapi.model.GeneroLivro;
import io.github.pedromartinsl.libraryapi.model.Livro;
import io.github.pedromartinsl.libraryapi.model.Usuario;
import io.github.pedromartinsl.libraryapi.repository.LivroRepository;
import io.github.pedromartinsl.libraryapi.security.SecurityService;
import io.github.pedromartinsl.libraryapi.validor.LivroValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
    private final LivroValidator validator;
    private final SecurityService securityService;

    public Livro salvar(Livro livro) {
        validator.validar(livro);
        Usuario usuario = securityService.obterUsuarioLogado();
        livro.setUsuario(usuario);
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Livro livro){
        repository.delete(livro);
    }

    public Page<Livro> pesquisa(
        String isbn, 
        String titulo, 
        String nomeAutor, 
        GeneroLivro genero, 
        Integer anoPublicacao,
        Integer pagina,
        Integer tamanhoPagina) {
        //pode ser substituido pelo Example

        //Retornando todos os elementos passados em where 0=0 > onde for verdadeiro pelo param
        Specification<Livro> specs = Specification
        .where((root, query, cb) -> cb.conjunction());

        if (isbn != null) {
            specs = specs.and(isbnEqual(isbn));
        }

        if (titulo != null) {
            specs = specs.and(tituloLike(titulo));
        }

        if (genero != null) {
            specs = specs.and(generoEqual(genero));
        }

        if (anoPublicacao != null) {
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }

        if (nomeAutor != null) {
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return repository.findAll(specs, pageRequest);
    }

    public void atualizar(Livro livro) {
        if (livro.getId() == null) {
            throw new IllegalArgumentException("Para atualizar é necessário que o livro já esteja necessário na base");
        }

        validator.validar(livro);
        repository.save(livro);
    }
}
