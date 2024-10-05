package br.com.sistema_gerenciamento.repository;

import br.com.sistema_gerenciamento.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
}
