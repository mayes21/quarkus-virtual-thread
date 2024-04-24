package dev.amabb.config;

import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;

//@Startup
@ApplicationScoped
public class InitDb {

    private final PgPool pgPool;

    public InitDb(PgPool pgPool) {
        this.pgPool = pgPool;
    }

    void config(@Observes StartupEvent ev) {
        initDb();
    }

    private void initDb() {
        List<Tuple> persons = new ArrayList<>(1000);
        Faker faker = new Faker();
        for (int i = 0; i < 1000; i++) {
            String name = faker.name().fullName();
            String gender = faker.gender().binaryTypes().toUpperCase();
            int age = faker.number().numberBetween(18, 65);
            int externalId = faker.number().numberBetween(100000, 999999);
            persons.add(Tuple.of(name, age, gender, externalId));
        }

        pgPool.query("DROP TABLE IF EXISTS person").execute()
                .flatMap(r -> pgPool.query("""
                  create table person (
                    id serial primary key,
                    name varchar(255),
                    gender varchar(255),
                    age int,
                    external_id int
                  )
                  """).execute())
                .flatMap(r -> pgPool
                        .preparedQuery("insert into person(name, age, gender, external_id) values($1, $2, $3, $4)")
                        .executeBatch(persons))
                .await().indefinitely();
    }
}
