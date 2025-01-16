package io.github.pedromartinsl.libraryapi.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.github.pedromartinsl.libraryapi.model.Autor;
import io.github.pedromartinsl.libraryapi.model.Livro;


public interface LivroRepository extends JpaRepository<Livro,UUID>, JpaSpecificationExecutor<Livro> {
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    boolean existsByAutor(Autor autor);
}
