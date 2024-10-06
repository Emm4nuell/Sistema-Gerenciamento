package br.com.sistema_gerenciamento.controller;

import br.com.sistema_gerenciamento.dto.UserRequest;
import br.com.sistema_gerenciamento.dto.UserResponse;
import br.com.sistema_gerenciamento.model.UserEntity;
import br.com.sistema_gerenciamento.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements IUserController{

    private final UserService userService;
    private final ObjectMapper mapper;

    @Override
    public ResponseEntity<Void> create(UserRequest request) {

        var service = userService.create(mapper.convertValue(request, UserEntity.class));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(service.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<UserResponse> findById(Long id) {
        UserResponse response = mapper.convertValue(userService.findById(id), UserResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<List<UserResponse>> findByAllPage(String page, String size, String sortBy) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> update(UserRequest request) {
        return null;
    }
}
