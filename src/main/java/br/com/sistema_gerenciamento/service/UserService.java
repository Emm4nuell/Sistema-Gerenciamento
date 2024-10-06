package br.com.sistema_gerenciamento.service;

import br.com.sistema_gerenciamento.exception.InvalidUserException;
import br.com.sistema_gerenciamento.model.UserEntity;
import br.com.sistema_gerenciamento.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final IUserRepository iUserRepository;


    /**
     * Criar um novo usuario no sistema
     *
     * @param userEntity cria um novo usuario.
     * @throws InvalidUserException gera um erro se o usuario for nulo.
     * @return O usuario criado
     */

    @Transactional
    public UserEntity create(UserEntity userEntity){
        if (userEntity == null){
            log.error("O User não pode ser NULL");
            throw new InvalidUserException("Falha ao criar usuário: o usuário fornecido é nulo. Por favor, forneça dados válidos.");
        }
        return iUserRepository.save(userEntity);
    }
}
