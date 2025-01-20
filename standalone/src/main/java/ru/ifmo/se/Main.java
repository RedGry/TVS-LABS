package ru.ifmo.se;

import ru.ifmo.se.repository.EntityManagerFactoryProvider;
import ru.ifmo.se.repository.PersonRepository;
import ru.ifmo.se.service.PersonService;
import ru.ifmo.se.soap.PersonWebService;

import jakarta.xml.ws.Endpoint;

import java.util.Map;


public class Main {
    public static void main(String[] args) {
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

        String url = env.getOrDefault("SOAP_SERVICE_URL", "http://localhost:8081/PersonService");
        Endpoint.publish(url, new PersonWebService(personService));

        System.out.println("Service started!");
    }
}
