package com.example.tacocloud.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.springframework.data.relational.core.mapping.Table;

import org.springframework.data.relational.core.mapping.Table;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table
public class Ingredient {
    @Id
    private String id;
    private String name;
    private Type type;

    public enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
