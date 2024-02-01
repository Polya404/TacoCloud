package com.example.tacocloud.messaging.impl.jms;

import com.example.tacocloud.messaging.OrderReceiver;
import com.example.tacocloud.models.TacoOrder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JmsOrderReceiver implements OrderReceiver {
    private final JmsTemplate jmsTemplate;

    @SneakyThrows
    @Override
    public TacoOrder receiveOrder() {
        return (TacoOrder) jmsTemplate.receiveAndConvert("tacocloud.order.queue");
    }
}
