package dev.amabb.model;

import io.vertx.mutiny.sqlclient.Row;

public record Person(Long id, String name, int age, Gender gender) {
    public static Person from(Row row) {
        return new Person(
                row.getLong("id"),
                row.getString("name"),
                row.getInteger("age"),
                Gender.valueOf(row.getString("gender")));
    }
}
