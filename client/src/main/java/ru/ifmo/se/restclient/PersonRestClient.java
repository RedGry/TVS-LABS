package ru.ifmo.se.restclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;
import java.util.List;

public class PersonRestClient {
    private final String baseUrl;
    private final Client client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PersonRestClient(String baseUrl, Client client) {
        this.baseUrl = baseUrl;
        this.client = client;
    }

    public List<PersonDto> searchPersons(String query, int limit, int offset) throws Exception {
        URI uri = UriBuilder.fromUri(baseUrl)
                .path("/persons")
                .queryParam("query", query.replace("%", "%25"))
                .queryParam("limit", limit)
                .queryParam("offset", offset)
                .build();

        Response response = client.target(uri.toURL().toString()).request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return response.readEntity(new GenericType<List<PersonDto>>() {});
        } else {
            throw new RuntimeException("Error fetching persons: " + response.readEntity(String.class));
        }
    }

    public PersonDto findPersonById(int id) throws Exception {
        URI uri = UriBuilder.fromUri(baseUrl)
                .path("/persons/{id}")
                .resolveTemplate("id", id)
                .build();
        Response response = client.target(uri.toURL().toString()).request(MediaType.APPLICATION_JSON).get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return response.readEntity(new GenericType<>(PersonDto.class));
        } else {
            throw new RuntimeException("Error fetching person: " + response.readEntity(String.class));
        }
    }

    public int createPerson(PersonDto person, String authHeader) throws Exception {
        Response response = client.target(baseUrl)
                .path("/persons")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", authHeader)
                .post(Entity.entity(person, MediaType.APPLICATION_JSON));

        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            return response.readEntity(Integer.class);
        } else {
            throw new RuntimeException("Error creating person: " + response.readEntity(String.class));
        }
    }

    public boolean updatePerson(int id, PersonDto person, String authHeader) throws Exception {
        Response response = client.target(baseUrl)
                .path("/persons/{id}")
                .resolveTemplate("id", id)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", authHeader)
                .put(Entity.entity(person, MediaType.APPLICATION_JSON));
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return true;
        } else if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return false;
        } else {
            throw new RuntimeException("Error updating person: " + response.readEntity(String.class));
        }
    }

    public boolean deletePerson(int id, String authHeader) throws Exception {
        Response response = client.target(baseUrl)
                .path("/persons/{id}")
                .resolveTemplate("id", id)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", authHeader)
                .delete();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return true;
        } else if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return false;
        } else {
            throw new RuntimeException("Error deleting person: " + response.readEntity(String.class));
        }
    }
}
