package io.github.pedromartinsl.libraryapi.service;

import org.springframework.stereotype.Service;

import io.github.pedromartinsl.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

    
}
