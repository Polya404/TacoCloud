package com.example.tacocloud.config;

import com.example.tacocloud.models.User;
import com.example.tacocloud.repositories.UserRepository;
import com.example.tacocloud.services.UserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class Config {
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return username -> {
            User user = userRepository.findByUsername(username);
            if (user != null) return user;
            throw new UsernameNotFoundException("User " + username + " not found");
        };
    }
}
