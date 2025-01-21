package ru.ifmo.se;

import jakarta.xml.ws.Binding;
import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.soap.SOAPBinding;
import ru.ifmo.se.repository.EntityManagerFactoryProvider;
import ru.ifmo.se.repository.PersonRepository;
import ru.ifmo.se.service.PersonService;
import ru.ifmo.se.soap.auth.BasicAuthHandler;
import ru.ifmo.se.soap.PersonWebService;

import jakarta.xml.ws.Endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Base64;


public class Main {
    public static void main(String[] args) {
        String encodedAuth = "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes());

        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");

        Map<String, String> env = System.getenv();
        String dbUrl = env.getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/postgres");
        String dbUser = env.getOrDefault("DB_USER", "postgres");
        String dbPassword = env.getOrDefault("DB_PASSWORD", "postgres");

        EntityManagerFactoryProvider entityManagerFactoryProvider = new EntityManagerFactoryProvider(
                dbUrl,
                dbUser,
                dbPassword
        );
        PersonRepository personRepository = new PersonRepository(entityManagerFactoryProvider.getEntityManagerFactory());
        PersonService personService = new PersonService(personRepository);

        String url = env.getOrDefault("SOAP_SERVICE_URL", "http://localhost:8080/PersonService");
        Endpoint endpoint = Endpoint.create(new PersonWebService(personService));
        Binding binding = endpoint.getBinding();
        if (binding instanceof SOAPBinding) {
            List<Handler> handlerChain = new ArrayList<>();
            handlerChain.add(new BasicAuthHandler(encodedAuth));
            binding.setHandlerChain(handlerChain);
        }
        endpoint.publish(url);

        System.out.println("Service started!");
    }
}
