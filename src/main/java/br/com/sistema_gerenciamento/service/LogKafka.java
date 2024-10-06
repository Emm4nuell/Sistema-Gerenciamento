package br.com.sistema_gerenciamento.service;

import br.com.sistema_gerenciamento.dto.LogErrorResponse;
import br.com.sistema_gerenciamento.exception.ConvertJsonMapperException;
import br.com.sistema_gerenciamento.exception.KafkaPublishingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Slf4j
public class LogKafka {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper mapper;
    @Value("${kafka.topic}")
    private String topic;
    
    public void publishLogError(LogErrorResponse error){
        log.error(error.toString());
        try {
            kafkaTemplate
                    .send(topic, mapper.writeValueAsString(error))
                    .thenAccept(response -> log.info("Sucesso ao publicar mensagem no kafka."))
                    .exceptionally(ex -> {
                        log.error("Erro ao publicar mensagem no kafka: {}", ex.getMessage());
                        return null;
                    });
        }  catch (JsonProcessingException e) {
            log.error("Erro ao converter LogErrorResponse para JSON: {}", e.getMessage());
            throw new ConvertJsonMapperException("Erro ao converter LogErrorResponse em JSON." + e);
        } catch (Exception e) {
            log.error("Erro inesperado ao enviar mensagem para o Kafka: {} ", e.getMessage());
            throw new KafkaPublishingException("Erro ao publicar mensagem no Kafka" + e);
        }

    }

}
