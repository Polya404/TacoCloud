package com.example.tacocloud.messaging.impl.jms;

import com.example.tacocloud.messaging.OrderMessagingService;
import com.example.tacocloud.models.TacoOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JmsOrderMessagingService implements OrderMessagingService {
    private final JmsTemplate jmsTemplate;

    @Override
    public void sendOrder(TacoOrder order) {
        jmsTemplate.convertAndSend(order, message -> {
            message.setStringProperty("X_ORDER_SOURCE", "WEB");
            return message;
        });
    }
}
