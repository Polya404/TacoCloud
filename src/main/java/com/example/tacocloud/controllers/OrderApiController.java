package com.example.tacocloud.controllers;

import com.example.tacocloud.models.TacoOrder;
import com.example.tacocloud.repositories.OrderRepository;
import com.example.tacocloud.messaging.OrderMessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/orders",
produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;
    private final OrderMessagingService messagingService;

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TacoOrder postOrder(@RequestBody TacoOrder order){
        messagingService.sendOrder(order);
        return orderRepository.save(order);
    }
}
