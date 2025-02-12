package io.github.pedromartinsl.libraryapi.repository;

import java.util.UUID;

import io.github.pedromartinsl.libraryapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID>{
    Usuario findByLogin(String login);
}
