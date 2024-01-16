package com.example.tacocloud.repositories;

import com.example.tacocloud.models.Taco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends JpaRepository<Taco, Long> {
}
