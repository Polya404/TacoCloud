package com.example.tacocloud.controllers;

import com.example.tacocloud.models.OrderProps;
import com.example.tacocloud.models.TacoOrder;
import com.example.tacocloud.models.User;
import com.example.tacocloud.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@SessionAttributes("tacoOrder")
@AllArgsConstructor
public class OrderController {

    private OrderRepository orderRepository;
    private OrderProps props;

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors,
                               SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        order.setUser(user);
        orderRepository.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @PostMapping("/deleteOrders")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

    @GetMapping("/getOrder")
    @PostAuthorize("hasRole('ADMIN') || returnObject.user.username == authentication.name")
    public TacoOrder getOrder(long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @GetMapping
    public String ordersForUser(
            @AuthenticationPrincipal User user, Model model) {
        Pageable pageable = PageRequest.of(0, props.getPageSize());
        model.addAttribute("orders", orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }

    @PutMapping(path = "/put/{orderId}", consumes = "application/json")
    public TacoOrder putOrder(@PathVariable("orderId") Long id,
                              @RequestBody TacoOrder order) {
        order.setId(id);
        return orderRepository.save(order);
    }

    @PatchMapping(path = "/patch/{orderId}", consumes = "application/json")
    public TacoOrder patchOrder(@PathVariable("orderId") Long id,
                                @RequestBody TacoOrder patch) {
        TacoOrder order = orderRepository.findById(id).orElseThrow();
        if (Objects.nonNull(patch.getDeliveryName())) {
            order.setDeliveryName(patch.getDeliveryName());
        }
        if (Objects.nonNull(patch.getDeliveryCity())) {
            order.setDeliveryName(patch.getDeliveryCity());
        }
        if (Objects.nonNull(patch.getDeliveryState())) {
            order.setDeliveryName(patch.getDeliveryState());
        }
        if (Objects.nonNull(patch.getDeliveryStreet())) {
            order.setDeliveryName(patch.getDeliveryStreet());
        }
        if (Objects.nonNull(patch.getDeliveryZip())) {
            order.setDeliveryName(patch.getDeliveryZip());
        }
        if (Objects.nonNull(patch.getCcCVV())) {
            order.setDeliveryName(patch.getCcCVV());
        }
        if (Objects.nonNull(patch.getCcExpiration())) {
            order.setDeliveryName(patch.getCcExpiration());
        }
        if (Objects.nonNull(patch.getCcNumber())) {
            order.setDeliveryName(patch.getCcNumber());
        }
        return orderRepository.save(order);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException ignored) {

        }
    }
}
