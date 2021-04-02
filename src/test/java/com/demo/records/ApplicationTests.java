package com.demo.records;

import com.google.common.collect.MoreCollectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
record ApplicationTests(TestRestTemplate restTemplate, UserRepository userRepository) {

    @Autowired
    ApplicationTests {
    }

    @Test
    void shouldGetAllUsers() {
        var request = RequestEntity.get("/users").accept(MediaType.APPLICATION_JSON).build();
        var response =
            restTemplate.exchange(request, new ParameterizedTypeReference<List<User>>() {
            });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(10);
    }

    @Test
    void shouldGetOneUser() {
        var users = new ArrayList<>(userRepository.getAll());
        Collections.shuffle(users);

        var user = users.stream().limit(1).collect(MoreCollectors.onlyElement());

        var request = RequestEntity.get("/users/{userId}", user.id()).accept(MediaType.APPLICATION_JSON).build();
        var response = restTemplate.exchange(request, User.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(user);
    }

    @Test
    void shouldReturn404WhenNotFound() {
        var request = RequestEntity.get("/users/{userId}", UUID.randomUUID()).accept(MediaType.APPLICATION_JSON).build();
        var response = restTemplate.exchange(request, User.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
