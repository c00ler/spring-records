package com.demo.records;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
    record UserController(UserRepository userRepository) {

    @GetMapping
    Collection<UserDto> getAll() {
        return userRepository.getAll()
            .stream()
            .map(User::toDto)
            .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    ResponseEntity<UserDto> findById(@PathVariable UUID userId) {
        return userRepository.findById(userId)
            .map(User::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
