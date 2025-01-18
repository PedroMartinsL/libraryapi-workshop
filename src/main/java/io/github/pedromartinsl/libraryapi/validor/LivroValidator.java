package io.github.pedromartinsl.libraryapi.validor;

import java.util.Optional;

import org.springframework.stereotype.Component;

import io.github.pedromartinsl.libraryapi.exceptions.CampoInvalidoException;
import io.github.pedromartinsl.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.pedromartinsl.libraryapi.model.Livro;
import io.github.pedromartinsl.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository repository;

    public void validar(Livro livro) {
        if (existeLivroComIsbn(livro)) {
            throw new RegistroDuplicadoException("isbn já cadastrado!");
        }

        if (isPrecoObrigatorioNulo(livro)) {
            throw new CampoInvalidoException("preco", "Para livros com ano de publicação a partir de 2020, o preço é obrigatório!");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null &&
                livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    private boolean existeLivroComIsbn(Livro livro) {
        Optional<Livro> livroEncontrado = repository.findByIsbn(livro.getIsbn());

        if (livro.getId() == null) {
            return livroEncontrado.isPresent();
        }

        return livroEncontrado
            .map(Livro::getId)
            .stream()
            .anyMatch(id -> !id.equals(livro.getId()));
    }
}
