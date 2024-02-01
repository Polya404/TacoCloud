package com.example.tacocloud.messaging;

import com.example.tacocloud.models.TacoOrder;

public interface OrderMessagingService {
    void sendOrder(TacoOrder order);
}
