package br.com.sistema_gerenciamento.controller;

import br.com.sistema_gerenciamento.dto.UserRequest;
import br.com.sistema_gerenciamento.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("user/")
public interface IUserController {

    @PostMapping("create")
    ResponseEntity<Void> create(@RequestBody UserRequest request);

    @GetMapping("findById/{id}")
    ResponseEntity<UserResponse> findById(@PathVariable Long id);

    @GetMapping("lista")
    ResponseEntity<List<UserResponse>> findByAllPage(@RequestParam(defaultValue = "0") String page,
                                                     @RequestParam(defaultValue = "5") String size,
                                                     @RequestParam(defaultValue = "id") String sortBy);

    @DeleteMapping("delete/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

    @PatchMapping("update/{id}")
    ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @RequestBody UserRequest request);
}
