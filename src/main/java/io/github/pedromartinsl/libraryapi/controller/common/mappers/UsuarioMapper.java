package io.github.pedromartinsl.libraryapi.controller.common.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.pedromartinsl.libraryapi.controller.dto.UsuarioDTO;
import io.github.pedromartinsl.libraryapi.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    Usuario toEntity(UsuarioDTO dto);
}
