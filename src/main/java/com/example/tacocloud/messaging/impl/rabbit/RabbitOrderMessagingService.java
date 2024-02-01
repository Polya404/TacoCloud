package com.example.tacocloud.messaging.impl.rabbit;

import com.example.tacocloud.messaging.OrderMessagingService;
import com.example.tacocloud.models.TacoOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitOrderMessagingService implements OrderMessagingService {
    private final RabbitTemplate rabbitTemplate;
    @Override
    public void sendOrder(TacoOrder order) {
//        MessageConverter converter = rabbitTemplate.getMessageConverter();
//        MessageProperties properties = new MessageProperties();
//        Message message = converter.toMessage(order, properties);
//        rabbitTemplate.send(message);
        rabbitTemplate.convertAndSend(order);
    }
}
