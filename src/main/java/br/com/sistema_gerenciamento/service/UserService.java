package br.com.sistema_gerenciamento.service;

import br.com.sistema_gerenciamento.dto.LogErrorResponse;
import br.com.sistema_gerenciamento.exception.AlreadyExistsException;
import br.com.sistema_gerenciamento.exception.InvalidException;
import br.com.sistema_gerenciamento.exception.UserNotFoundException;
import br.com.sistema_gerenciamento.model.UserEntity;
import br.com.sistema_gerenciamento.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final IUserRepository iUserRepository;
    private final PublishSnsService snsService;
    private final LogKafka logKafka;


    /**
     * Criar um novo usuario no sistema
     *
     * @param userEntity cria um novo usuario.
     * @throws InvalidException gera um erro se o usuario for nulo.
     * @throws AlreadyExistsException gera um erro se houver email duplicado.
     * @return O usuario criado
     *
     * <p>Ao gerar um erro, o sistema vai fazer a chamada do kafka para para deixar registrado os logs de erro.</p>
     */

    @Transactional
    public UserEntity create(UserEntity userEntity){
        if (userEntity == null){
            String errorMessage = "Falha ao criar usuário: o usuário fornecido é nulo. Por favor, forneça dados válidos.";
            logKafka.publishLogError(new LogErrorResponse(
                    LocalDateTime.now(),
                    errorMessage));
            throw new InvalidException(errorMessage);
        }

        if (iUserRepository.findByEmail(userEntity.getEmail()).isPresent()){
            String errorMessage = "EMAIL: " + userEntity.getEmail() + " já cadastrado na base de dados. ";
            logKafka.publishLogError(new LogErrorResponse(
                    LocalDateTime.now(),
                    errorMessage));
            throw new AlreadyExistsException(errorMessage);
        }

        snsService.publishSuccess(new LogErrorResponse(
                LocalDateTime.now(),
                "Usuario criado com sucesso. EMAIL: " + userEntity.getEmail()));
        return iUserRepository.save(userEntity);
    }

    /**
     * Pesquisar usuario pelo ID
     *
     * @param id pesquisa um usuario na base de dados.
     * @throws InvalidException erro ao verificar que o id é nulo.
     * @throws UserNotFoundException gera um erro se o usuario não for localizado.
     * @return retorna um userEntity.
     *
     * <p>Ao gerar um erro, o sistema vai fazer a chamada do kafka para para deixar registrado os logs de erro.</p>
     *
     */

    public UserEntity findById(Long id) {
        if (id == null){
            String errorMessage = "Falha ao pesquisar o usuário: o ID fornecido é nulo. Por favor, forneça dados válidos.";
            logKafka.publishLogError(new LogErrorResponse(
                    LocalDateTime.now(),
                    errorMessage));
            throw new InvalidException(errorMessage);
        }
        return iUserRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMessage = "Usuário com ID: " + id + " não foi encontrado.";
                    logKafka.publishLogError(new LogErrorResponse(
                            LocalDateTime.now(),
                            errorMessage));
                    return new UserNotFoundException(errorMessage);
                });
    }

    /**
     * Deleta usuario pelo id
     *
     * @param id deletar usuario da base de dados.
     * @throws InvalidException erro se o id for nulo.
     * @throws UserNotFoundException erro se o usuario nao for localizado.
     *
     * <p>Ao gerar um erro, o sistema vai fazer a chamada do kafka para para deixar registrado os logs de erro.</p>
     *
     */

    @Transactional
    public void delete(Long id) {
        if (id == null){
            String errorMessage = "Falha ao deletar o usuário: o ID fornecido é nulo. Por favor, forneça dados válidos.";
            logKafka.publishLogError(new LogErrorResponse(
                    LocalDateTime.now(),
                    errorMessage));
            throw new InvalidException(errorMessage);
        }

        var entity = findById(id);
        iUserRepository.deleteById(entity.getId());
        snsService.publishSuccess(new LogErrorResponse(
                LocalDateTime.now(),
                "Usuario criado com sucesso. ID: " + id));
    }

    /**
     *
     * @param id verifica se o usuario existe para poder atualizar.
     * @param userEntity atualiza as informações do usuario na base de dados.
     * @throws InvalidException gera um erro se o o id ou userEntity for nulo.
     * @throws UserNotFoundException gera um erro se o usuario nao for localizado.
     * @return retorna um userEntity ja atualizado.
     *
     * <p>Ao gerar um erro, o sistema vai fazer a chamada do kafka para para deixar registrado os logs de erro.</p>
     *
     */

    @Transactional
    public UserEntity update(Long id, UserEntity userEntity) {
        if (id == null || userEntity == null){
            String errorMessage = "Falha ao atualizar o usuário: o ID ou o USUARIO fornecido é nulo. Por favor, forneça dados válidos.";
            logKafka.publishLogError(new LogErrorResponse(
                    LocalDateTime.now(),
                    errorMessage));
            throw new InvalidException(errorMessage);
        }

        if (iUserRepository.findById(id).isEmpty()){
            String errorMessage = "Usuário com ID " + id + " não foi encontrado.";
            logKafka.publishLogError(new LogErrorResponse(
                    LocalDateTime.now(),
                    errorMessage));
            throw new UserNotFoundException(errorMessage);
        }
        userEntity.setId(id);
        snsService.publishSuccess(new LogErrorResponse(
                LocalDateTime.now(),
                "Usuario atualizado com sucesso. ID: " + id));
        return iUserRepository.save(userEntity);
    }

    public Page<UserEntity> findAll(Pageable pageable) {
        return iUserRepository.findAll(pageable);
    }
}
