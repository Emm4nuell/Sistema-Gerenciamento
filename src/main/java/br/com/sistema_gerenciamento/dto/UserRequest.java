package br.com.sistema_gerenciamento.dto;

import lombok.Data;

@Data
public class UserRequest {
    private Long id;
    private String name;
    private String email;
}
