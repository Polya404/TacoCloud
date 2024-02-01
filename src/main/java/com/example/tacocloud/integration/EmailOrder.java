package com.example.tacocloud.integration;

import com.example.tacocloud.models.Taco;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmailOrder {
    private final String email;
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco){
        this.tacos.add(taco);
    }
}
