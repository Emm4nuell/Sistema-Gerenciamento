package br.com.sistema_gerenciamento.repository;

import br.com.sistema_gerenciamento.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<String> findByEmail(String email);
}
