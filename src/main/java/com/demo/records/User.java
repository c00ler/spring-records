package com.demo.records;

import java.util.UUID;

record User(UUID id, String firstName, String lastName, long createdAt) {
}
