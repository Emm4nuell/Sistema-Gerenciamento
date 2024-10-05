package br.com.sistema_gerenciamento.controller;

import br.com.sistema_gerenciamento.dto.UserRequest;
import br.com.sistema_gerenciamento.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements IUserController{

    @Override
    public ResponseEntity<Void> create(UserRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponse> findById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<UserResponse>> findByAllPage(String page, String size, String sortBy) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> update(UserRequest request) {
        return null;
    }
}
