package com.example.tacocloud.messaging.impl.kafka;

import com.example.tacocloud.messaging.OrderMessagingService;
import com.example.tacocloud.models.TacoOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaOrderMessagingService implements OrderMessagingService {
    private final KafkaTemplate<String, TacoOrder> kafkaTemplate;
    @Override
    public void sendOrder(TacoOrder order) {
        kafkaTemplate.sendDefault(order);
    }
}
