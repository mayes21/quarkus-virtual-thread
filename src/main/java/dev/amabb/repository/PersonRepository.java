package dev.amabb.repository;

import dev.amabb.model.Person;
import io.quarkus.logging.Log;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PersonRepository {

    private final PgPool pgPool; // PgPool client allows to create and execute SQL queries in a non-blocking way

    public PersonRepository(PgPool pgPool) {
        this.pgPool = pgPool;
    }

    public Person save(Person person) {
        Long id = pgPool
                .preparedQuery("INSERT INTO person(name, age, gender) VALUES ($1, $2, $3) RETURNING id")
                .executeAndAwait()// perform the operation asynchronously
                .iterator().next().getLong("id");
        return new Person(id, person.name(), person.age(), person.gender());
    }

    public List<Person> findAll() {
        Log.info("FindAll()" + Thread.currentThread());
        RowSet<Row> rowSet = pgPool
                .preparedQuery("SELECT id, name, age, gender FROM person")
                .executeAndAwait(); // perform the operation asynchronously
        return iterateAndCreate(rowSet);
    }

    public Person findById(Long id) {
        RowSet<Row> rowSet = pgPool
                .preparedQuery("SELECT id, name, age, gender FROM person WHERE id = $1")
                .executeAndAwait(Tuple.of(id)); // perform the operation asynchronously
        List<Person> persons = iterateAndCreate(rowSet);
        return persons.isEmpty() ? null : persons.get(0);
    }

    public List<Person> findByName(String name) {
        RowSet<Row> rowSet = pgPool
                .preparedQuery("SELECT id, name, age, gender FROM person WHERE id = $1")
                .executeAndAwait(Tuple.of(name)); // perform the operation asynchronously
        return iterateAndCreate(rowSet);
    }

    public List<Person> findByAgeGreaterThan(int age) {
        RowSet<Row> rowSet = pgPool
                .preparedQuery("SELECT id, name, age, gender FROM person WHERE age > $1")
                .executeAndAwait(Tuple.of(age)); // perform the operation asynchronously
        return iterateAndCreate(rowSet);
    }

    private List<Person> iterateAndCreate(RowSet<Row> rowSet) {
        List<Person> persons = new ArrayList<>();
        for (Row row : rowSet) {
            persons.add(Person.from(row));
        }
        return persons;
    }

}
