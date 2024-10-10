package br.com.sistema_gerenciamento.service;

import br.com.sistema_gerenciamento.dto.LogErrorResponse;
import br.com.sistema_gerenciamento.exception.ConvertJsonMapperException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsAsyncClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublishSnsService {

    private final SnsAsyncClient snsAsyncClient;
    private final ObjectMapper mapper;
    private final LogKafka logKafka;
    @Value("${aws.config.topic-arn}")
    private String topic;

    public void publishSuccess(LogErrorResponse response){

        if (topic == null || topic.isEmpty()){
            String message = "O topic fornecido é nulo. Por favor, forneça dados válidos.";
            logKafka.publishLogError(new LogErrorResponse(
                    LocalDateTime.now(),
                    message
            ));
        }

        try {
            PublishRequest request = PublishRequest.builder()
                    .topicArn(topic)
                    .subject("Object")
                    .message(mapper.writeValueAsString(response))
                    .build();
            snsAsyncClient.publish(request)
                    .thenAccept(res -> log.info("Mensagem enviada com sucesso."))
                    .exceptionally(ex -> {
                        log.error("Erro ao enviar topic {} ", ex.getMessage());
                        return null;});
        } catch (JsonProcessingException e) {
            throw new ConvertJsonMapperException("Erro ao converter Object em JSON: " + response.getMessage());
        }
    }

}
