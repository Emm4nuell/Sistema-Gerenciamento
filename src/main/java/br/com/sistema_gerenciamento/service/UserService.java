package br.com.sistema_gerenciamento.service;

import br.com.sistema_gerenciamento.exception.AlreadyExistsException;
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
     * @throws AlreadyExistsException gera um erro se houver email duplicado.
     * @return O usuario criado
     */

    @Transactional
    public UserEntity create(UserEntity userEntity){
        if (userEntity == null){
            log.error("O usuario não pode ser NULL");
            throw new InvalidException("Falha ao criar usuário: o usuário fornecido é nulo. Por favor, forneça dados válidos.");
        }

        if (iUserRepository.findByEmail(userEntity.getEmail()).isPresent()){
            log.error("EMAIL: {} já cadastrado na base de dados.", userEntity.getEmail());
            throw new AlreadyExistsException("EMAIL: " + userEntity.getEmail() + " já cadastrado na base de dados. ");
        }

        return iUserRepository.save(userEntity);
    }

    /**
     * Pesquisar usuario pelo ID
     *
     * @param id pesquisa um usuario na base de dados.
     * @throws InvalidException erro ao verificar que o id é nulo.
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

    /**
     * Deleta usuario pelo id
     *
     * @param id deletar usuario da base de dados.
     * @throws InvalidException erro se o id for nulo.
     * @throws UserNotFoundException erro se o usuario nao for localizado.
     */

    public void delete(Long id) {
        if (id == null){
            log.error("O ID não pode ser nulo");
            throw new InvalidException("O ID não pode ser nulo.");
        }

        var entity = findById(id);
        iUserRepository.deleteById(entity.getId());
    }

    /**
     *
     * @param id verifica se o usuario existe para poder atualizar.
     * @param userEntity atualiza as informações do usuario na base de dados.
     * @throws InvalidException gera um erro se o o id ou userEntity for nulo.
     * @throws UserNotFoundException gera um erro se o usuario nao for localizado.
     * @return retorna um userEntity ja atualizado.
     */

    @Transactional
    public UserEntity update(Long id, UserEntity userEntity) {
        if (id == null || userEntity == null){
            log.error("O ID: {} ou USUARIO: {} não pode ser nulo", id, userEntity);
            throw new InvalidException("O ID ou USUARIO não pode ser nulo.");
        }

        if (iUserRepository.findById(id).isEmpty()){
            log.error("Usuário com ID: {} não foi encontrado.", id);
            throw new UserNotFoundException("Usuário com ID " + id + " não foi encontrado.");
        }
        userEntity.setId(id);
        return iUserRepository.save(userEntity);
    }
}
