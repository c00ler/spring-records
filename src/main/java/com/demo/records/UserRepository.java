package com.demo.records;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
record UserRepository(ConcurrentMap<UUID, User> users) {

    @Autowired
    UserRepository(Faker faker) {
        this(initialize(faker));
    }

    Collection<User> getAll() {
        return List.copyOf(users.values());
    }

    Optional<User> findById(UUID userId) {
        return Optional.ofNullable(users.get(userId));
    }

    private static ConcurrentMap<UUID, User> initialize(Faker faker) {
        return IntStream.range(0, 10)
            .mapToObj(__ -> new User(UUID.randomUUID(), faker.name().firstName(), faker.name().lastName()))
            .collect(Collectors.toConcurrentMap(User::id, Function.identity()));
    }
}
