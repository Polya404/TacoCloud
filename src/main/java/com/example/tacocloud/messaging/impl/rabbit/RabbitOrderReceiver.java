package com.example.tacocloud.messaging.impl.rabbit;

import com.example.tacocloud.messaging.OrderReceiver;
import com.example.tacocloud.models.TacoOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitOrderReceiver implements OrderReceiver {
    private final RabbitTemplate rabbitTemplate;
    @Override
    public TacoOrder receiveOrder() {
        return rabbitTemplate.receiveAndConvert("tacocloud.order", new ParameterizedTypeReference<>() {
        });
    }
}
