package ru.ifmo.se.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.se.model.dto.PersonDto;
import ru.ifmo.se.model.entity.Person;
import ru.ifmo.se.service.PersonService;
import ru.ifmo.se.service.PersonValidationService;

import java.util.List;

@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonRestController {
    private final PersonService personService;

    @Inject
    public PersonRestController(PersonService personService) {
        this.personService = personService;
    }

    @GET
    public Response searchPersons(@QueryParam("query") String query,
                                  @QueryParam("limit") @DefaultValue("10") int limit,
                                  @QueryParam("offset") @DefaultValue("0") int offset) {
        List<Person> persons = personService.searchPersons(query, limit, offset);
        return Response.ok(persons).build();
    }

    @GET
    @Path("/{id}")
    public Response findPersonById(@PathParam("id") int id) {
        Person person = personService.readPerson(id);
        return person != null ? Response.ok(person).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response createPerson(PersonDto personDto) {
        PersonValidationService.validatePersonDto(personDto);
        int id = personService.createPerson(personDto);
        return Response.status(Response.Status.CREATED).entity(id).build();
    }

    @PUT
    @Path("/{id}")
    public Response updatePerson(@PathParam("id") int id, PersonDto personDto) {
        PersonValidationService.validatePersonDto(personDto);
        boolean updated = personService.updatePerson(id, personDto);
        return updated ? Response.ok().build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePersonById(@PathParam("id") int id) {
        boolean deleted = personService.deletePersonById(id);
        return deleted ? Response.ok().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}

