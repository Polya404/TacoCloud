package com.example.tacocloud.models;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "taco.orders")
@Component
@Data
public class OrderProps {
    private int pageSize = 20;
}
