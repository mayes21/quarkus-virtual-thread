package dev.amabb;

import dev.amabb.model.Person;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class PersonResourceTest {
    @Test
    void findAll() {
        given()
                .when().get("/persons")
                .then()
                .statusCode(200)
                .assertThat().body("size()", is(1000));
    }

    @Test
    void findById() {
        Person person = given()
                .when().get("/persons/1")
                .then()
                .statusCode(200)
                .extract()
                .body().as(Person.class);
        assertNotNull(person);
        assertEquals(1L, person.id());
    }
}
