package br.com.sistema_gerenciamento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LogErrorResponse implements Serializable {
    private LocalDateTime timestamp;
    private String message;
}
