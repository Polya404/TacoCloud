package com.example.tacocloud.repositories;

import com.example.tacocloud.models.User;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
//@EnableJpaRepositories
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
