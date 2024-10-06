package br.com.sistema_gerenciamento.controller;

import br.com.sistema_gerenciamento.dto.UserRequest;
import br.com.sistema_gerenciamento.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("user/")
public interface IUserController {

    @PostMapping("create")
    ResponseEntity<Void> create(@RequestBody UserRequest request);

    @GetMapping("findById/{id}")
    ResponseEntity<UserResponse> findById(@PathVariable Long id);

    @GetMapping("findAll")
    ResponseEntity<Page<UserResponse>> findByAllPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "direction", defaultValue = "asc") String direction);

    @DeleteMapping("delete/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

    @PatchMapping("update/{id}")
    ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @RequestBody UserRequest request);
}
