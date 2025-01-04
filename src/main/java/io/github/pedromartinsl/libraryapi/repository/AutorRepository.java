package io.github.pedromartinsl.libraryapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.pedromartinsl.libraryapi.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

}
