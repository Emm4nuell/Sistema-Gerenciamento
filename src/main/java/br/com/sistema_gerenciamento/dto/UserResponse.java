package br.com.sistema_gerenciamento.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
}
