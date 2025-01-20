package ru.ifmo.se.security;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Provider
public class BasicAuthFilter implements ContainerRequestFilter {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if ("GET".equals(requestContext.getRequest().getMethod())) {return;}

        String authorization = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorization == null || !authorization.startsWith("Basic ")) {
            abortWithUnauthorized(requestContext);
            return;
        }

        String credentials = new String(Base64.getDecoder().decode(authorization.substring(6)), StandardCharsets.UTF_8);
        String[] userPass = credentials.split(":", 2);

        if (userPass.length != 2 || !USERNAME.equals(userPass[0]) || !PASSWORD.equals(userPass[1])) {
            abortWithUnauthorized(requestContext);
        }
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        Response unauthorized = Response.status(Response.Status.UNAUTHORIZED)
                .entity("User cannot access the resource.")
                .build();
        requestContext.abortWith(unauthorized);
    }
}
