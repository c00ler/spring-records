package com.demo.records;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
record ApplicationTests(TestRestTemplate restTemplate) {

    @Autowired
    ApplicationTests {
    }

    @Test
    void shouldReturn404WhenNotFound() {
        var request = RequestEntity.get("/users/{userId}", UUID.randomUUID()).accept(MediaType.APPLICATION_JSON).build();
        var response = restTemplate.exchange(request, User.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
