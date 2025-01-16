package io.github.pedromartinsl.libraryapi.controller.common.mappers;

import org.mapstruct.Mapper;

import io.github.pedromartinsl.libraryapi.controller.dto.AutorDTO;
import io.github.pedromartinsl.libraryapi.model.Autor;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto); 
    AutorDTO toDTO(Autor autor);
 }
