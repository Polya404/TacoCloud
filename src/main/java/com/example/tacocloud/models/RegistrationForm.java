package com.example.tacocloud.models;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;


public record RegistrationForm(String username, String password, String fullName, String street, String city,
                               String state, String zip, String phoneNumber) {
    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password),
                fullName, street, city, state, zip, phoneNumber);
    }
}
