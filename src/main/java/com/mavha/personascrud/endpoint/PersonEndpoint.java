package com.mavha.personascrud.endpoint;

import com.mavha.personascrud.domain.Person;
import com.mavha.personascrud.service.PersonService;
import com.mavha.personascrud.validation.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

@Path("/")
@Component
public class PersonEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(PersonEndpoint.class);

    private final PersonService personService;

    public PersonEndpoint(PersonService personService) {
        this.personService = personService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPerson(Person person){

        Validate.notNull(person, "Empty request is not allowed");

        person = personService.createPerson(person);

        logger.info("Created a new person: {}", person);

        URI createdPersonUri = UriBuilder.fromResource(PersonEndpoint.class).path(String.valueOf(person.getDni()))
                               .build();

        return Response.created(createdPersonUri).entity(person).build();
    }

    @POST
    @Path("/{dni}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPersonWithDni(@PathParam("dni") int dni){

        Person person = new Person();
        person.setDni(dni);

        Validate.notNull(person, "Empty request is not allowed");

        person = personService.createPerson(person);

        logger.info("Created a new person: {}", person);

        URI createdPersonUri = UriBuilder.fromResource(PersonEndpoint.class).path(String.valueOf(person.getDni()))
                .build();

        return Response.created(createdPersonUri).entity(person).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersons(){
        List<Person> personList = personService.findPersons();

        return Response.ok(personList).build();
    }
}
