package br.com.sistema_gerenciamento.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
}
