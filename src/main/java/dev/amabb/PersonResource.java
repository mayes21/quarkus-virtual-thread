package dev.amabb;

import dev.amabb.model.Person;
import dev.amabb.repository.PersonRepository;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.List;

@Path("/persons")
@RunOnVirtualThread
public class PersonResource {
    private final PersonRepository personRepository;

    public PersonResource(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @POST
    public Person addPerson(Person person) {
        person = personRepository.save(person);
        return person;
    }

    @GET
    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    @GET
    @Path("/name/{name}")
    public List<Person> getPersonsByName(@PathParam("name") String name) {
        return personRepository.findByName(name);
    }

    @GET
    @Path("/age-greater-than/{age}")
    public List<Person> getPersonsByName(@PathParam("age") int age) {
        return personRepository.findByAgeGreaterThan(age);
    }

    @GET
    @Path("/{id}")
    public Person getPersonById(@PathParam("id") Long id) {
        Log.infov("(%s) getPersonById(%d)", Thread.currentThread(), id);
        return personRepository.findById(id);
    }
}
