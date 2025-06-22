package com.literalura.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Exceção para recurso não encontrado (exemplo: NoSuchElementException)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErroResposta> handleNoSuchElementException(NoSuchElementException ex) {
        ErroResposta erro = new ErroResposta(
                "Recurso não encontrado",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // Exceção para validação de dados (javax.validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            erros.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(erros);
    }

    // Exceção para argumentos inválidos em geral
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroResposta> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErroResposta erro = new ErroResposta(
                "Requisição inválida",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // Tratamento para outras exceções genéricas não previstas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handleGenericException(Exception ex) {
        ErroResposta erro = new ErroResposta(
                "Erro interno no servidor",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}
