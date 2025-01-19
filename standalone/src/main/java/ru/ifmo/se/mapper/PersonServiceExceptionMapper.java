package ru.ifmo.se.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ru.ifmo.se.controller.errors.PersonServiceException;

@Provider
public class PersonServiceExceptionMapper implements ExceptionMapper<PersonServiceException> {

    @Override
    public Response toResponse(PersonServiceException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(exception.getFaultInfo())
                .build();
    }
}
