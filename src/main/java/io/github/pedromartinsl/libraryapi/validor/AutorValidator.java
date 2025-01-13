package io.github.pedromartinsl.libraryapi.validor;

import java.util.Optional;

import org.springframework.stereotype.Component;

import io.github.pedromartinsl.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.pedromartinsl.libraryapi.model.Autor;
import io.github.pedromartinsl.libraryapi.repository.AutorRepository;

@Component
public class AutorValidator {

    private AutorRepository repository;

    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor) {
        
        if (existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado");
        }
    }

    private boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorEncontrado = repository.findByNomeAndDataNascimentoAndNacionalidade(autor.getNome(),
                autor.getDataNascimento(), autor.getNacionalidade());

        if (autor.getId() == null) {
            return autorEncontrado.isPresent();
        }

        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }
}
