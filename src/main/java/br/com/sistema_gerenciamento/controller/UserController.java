package br.com.sistema_gerenciamento.controller;

import br.com.sistema_gerenciamento.dto.UserRequest;
import br.com.sistema_gerenciamento.dto.UserResponse;
import br.com.sistema_gerenciamento.model.UserEntity;
import br.com.sistema_gerenciamento.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<Page<UserResponse>> findByAllPage(int page, int size, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.fromString(direction),
                sortBy));
        Page<UserEntity> pages = userService.findAll(pageable);
        List<UserResponse> response = pages.stream()
                .map(x -> mapper.convertValue(x, UserResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(response, pageable, pages.getTotalElements()));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<UserResponse> update(Long id, UserRequest request) {
        var response = userService.update(id, mapper.convertValue(request, UserEntity.class));
        return ResponseEntity.status(HttpStatus.OK).body(mapper.convertValue(request, UserResponse.class));
    }
}
