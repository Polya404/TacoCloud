package com.example.tacocloud.integration;

import lombok.AllArgsConstructor;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class OrderSubmitMessageHandler implements GenericHandler<EmailOrder> {
    private RestTemplate restTemplate;
    private ApiProperties apiProperties;

    @Override
    public Object handle(EmailOrder payload, MessageHeaders headers) {
        restTemplate.postForObject(apiProperties.getUrl(), payload, String.class);
        return null;
    }
}
