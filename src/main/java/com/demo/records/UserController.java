package com.demo.records;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/users")
record UserController(UserRepository userRepository) {

    @GetMapping
    Collection<User> getAll() {
        return userRepository.getAll();
    }

    @GetMapping("/{userId}")
    ResponseEntity<User> findById(@PathVariable UUID userId) {
        return userRepository.findById(userId)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
