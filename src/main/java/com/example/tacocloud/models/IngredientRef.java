package com.example.tacocloud.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.springframework.data.relational.core.mapping.Table;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table
public class IngredientRef {
    @Id
    private final String ingredient;
}
