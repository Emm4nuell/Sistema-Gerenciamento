package br.com.sistema_gerenciamento.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, Object>> nullPointerException(
            NullPointerException exception,
            HttpServletRequest http){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(getError(
                        exception.getMessage(),
                        "Informaçoes nulas",
                        HttpStatus.BAD_REQUEST.value(),
                        http.getRequestURI()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> httpMessageNotReadableException(
            HttpMessageNotReadableException exception,
            HttpServletRequest http){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(getError(
                        exception.getMessage(),
                        "Por favor, forneça dados válidos.",
                        HttpStatus.BAD_REQUEST.value(),
                        http.getRequestURI()));
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<Map<String, Object>> invalidUserException(
            InvalidUserException exception,
            HttpServletRequest http){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(getError(
                        exception.getMessage(),
                        "Por favor, forneça dados válidos.",
                        HttpStatus.BAD_REQUEST.value(),
                        http.getRequestURI()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> userNotFoundException(
            UserNotFoundException exception,
            HttpServletRequest http){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(getError(
                        exception.getMessage(),
                        "Dados não localizado na base de dados.",
                        HttpStatus.NOT_FOUND.value(),
                        http.getRequestURI()));
    }

    public Map<String, Object> getError(String message, String error, int status, String path){
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("timestamp", LocalDateTime.now());
        response.put("message", message);
        response.put("error", error);
        response.put("path", path);
        return response;
    }
}
