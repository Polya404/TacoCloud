package com.example.tacocloud.dto;

import com.example.tacocloud.models.TacoOrder;

public class KitchenUi {
    public void displayOrder(TacoOrder tacoOrder) {
        System.out.println(tacoOrder.toString());
    }
}
