package br.com.sistema_gerenciamento.exception;

public class InvalidException extends RuntimeException{
    public InvalidException(String message) {
        super(message);
    }
}
