package br.com.sistema_gerenciamento.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsAsyncClient;

import java.time.Duration;

@Configuration
public class AwsConfig {

    @Value("${aws.config.accessKey}")
    private String access;
    @Value("${aws.config.secretKey}")
    private String secret;
    @Value("${aws.config.region}")
    private String region;


    @Bean
    public SnsAsyncClient snsAsyncClient(){
        return SnsAsyncClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(access, secret)))
                .region(Region.US_EAST_1)
                .overrideConfiguration(ClientOverrideConfiguration.builder()
                        .apiCallTimeout(Duration.ofSeconds(30)) // Tempo total para a chamada da API
                        .apiCallAttemptTimeout(Duration.ofSeconds(10)) // Tempo para cada tentativa de chamada
                        .build())
                .build();
    }

}
