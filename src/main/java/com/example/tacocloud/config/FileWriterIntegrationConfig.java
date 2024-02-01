package com.example.tacocloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.GenericTransformer;

import java.io.File;

@Configuration
public class FileWriterIntegrationConfig {
    @Bean
    public IntegrationFlow fileWriterFlow(){
        return IntegrationFlows
                .from(MessageChannels.direct("textInChannel"))
                .channel(MessageChannels.direct("FileWriterChannel"))
                .<String, String>transform(String::toUpperCase)
                .handle(Files.outboundAdapter(new File("/home/polina/Programming/MyProjects/TacoCloud/src/main/java/com/example/tacocloud/files"))
                        .appendNewLine(true)).get();
    }
}
