package com.example.tacocloud.messaging.impl;

import com.example.tacocloud.dto.KitchenUi;
import com.example.tacocloud.models.TacoOrder;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Component
@Profile("jms-listener")
@Log4j2
public class OrderListener {
    private KitchenUi kitchenUi;

    @JmsListener(destination = "tacocloud.order.queue")
    public void receiveOrderFromJms(TacoOrder tacoOrder){
        kitchenUi.displayOrder(tacoOrder);
    }

    @RabbitListener(queues = "tacocloud.order.queue")
    public void receiveOrderFromRabbit(TacoOrder tacoOrder){
        kitchenUi.displayOrder(tacoOrder);
    }

    @KafkaListener(topics = "tacocloud.order.topic")
    public void receiveOrderFromKafka(TacoOrder tacoOrder, Message<TacoOrder> message){
        MessageHeaders headers = message.getHeaders();
        log.info("Received from partition {} with timestamp {}",
                headers.get(KafkaHeaders.RECEIVED_PARTITION_ID),
                headers.get(KafkaHeaders.RECEIVED_TIMESTAMP));
        kitchenUi.displayOrder(tacoOrder);
    }
}
