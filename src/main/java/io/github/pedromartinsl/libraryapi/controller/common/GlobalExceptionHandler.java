package io.github.pedromartinsl.libraryapi.controller.common;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.pedromartinsl.libraryapi.controller.dto.ErroCampo;
import io.github.pedromartinsl.libraryapi.controller.dto.ErroResposta;
import io.github.pedromartinsl.libraryapi.exceptions.CampoInvalidoException;
import io.github.pedromartinsl.libraryapi.exceptions.OperacaoNegadaException;
import io.github.pedromartinsl.libraryapi.exceptions.RegistroDuplicadoException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Erro de validação: {}", e.getMessage());
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaErros = fieldErrors.stream().map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage())).collect(Collectors.toList());
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), 
        "Erro de validação", 
        listaErros);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException e) {
        return ErroResposta.confito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNegadaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPermitidaException(OperacaoNegadaException e) {
        return ErroResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(CampoInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleCampoInvalidoException(CampoInvalidoException e) {
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação.", List.of(new ErroCampo(e.getCampo(), e.getMessage())));
        //Dessa forma, pegamos o campo do CampoInvalidoException e atribuimos a lista
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroResposta handleAccessDeniedException(AccessDeniedException e) {
        return new ErroResposta(HttpStatus.FORBIDDEN.value(), "Access denied.", List.of());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleErrosNaoTratados(RuntimeException e) {
        log.error("Erro inesperado", e);
        return new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Ocorreu um erro inesperado, entre em contato com a administração do sistema", List.of());
    }
} 
