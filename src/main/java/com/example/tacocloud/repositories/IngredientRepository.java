package com.example.tacocloud.repositories;

import com.example.tacocloud.models.Ingredient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
//@EnableJpaRepositories
public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
