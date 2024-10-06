package br.com.sistema_gerenciamento.service;

import br.com.sistema_gerenciamento.exception.InvalidException;
import br.com.sistema_gerenciamento.exception.UserNotFoundException;
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
     * @throws InvalidException gera um erro se o usuario for nulo.
     * @return O usuario criado
     */

    @Transactional
    public UserEntity create(UserEntity userEntity){
        if (userEntity == null){
            log.error("O User não pode ser NULL");
            throw new InvalidException("Falha ao criar usuário: o usuário fornecido é nulo. Por favor, forneça dados válidos.");
        }
        return iUserRepository.save(userEntity);
    }

    /**
     * Pesquisar usuario pelo ID
     *
     * @param id pesquisa um usuario na base de dados.
     * @throws InvalidException erro ao verificar que o id e nulo.
     * @throws UserNotFoundException gera um erro se o usuario não for localizado.
     * @return retorna um userEntity.
     */

    public UserEntity findById(Long id) {
        if (id == null){
            log.error("O ID não pode ser nulo");
            throw new InvalidException("O ID não pode ser nulo.");
        }
        return iUserRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Usuário com ID: {} não foi encontrado.", id);
                    return new UserNotFoundException("Usuário com ID " + id + " não foi encontrado.");
                });
    }
}
