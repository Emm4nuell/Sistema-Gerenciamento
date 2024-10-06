package br.com.sistema_gerenciamento.exception;

public class KafkaPublishingException extends RuntimeException{
    public KafkaPublishingException(String message) {
        super(message);
    }
}
