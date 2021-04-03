package com.demo.records;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.Clock;

@SpringBootApplication
public class Application {

    @Bean
    Faker faker() {
        return Faker.instance();
    }

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
            .serializationInclusion(JsonInclude.Include.NON_ABSENT);
// https://github.com/FasterXML/jackson-databind/issues/2992
//            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
