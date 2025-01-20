package ru.ifmo.se;

import com.sun.net.httpserver.HttpServer;
import jakarta.ws.rs.core.UriBuilder;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import ru.ifmo.se.controller.PersonRestController;
import ru.ifmo.se.mapper.PersonServiceExceptionMapper;
import ru.ifmo.se.repository.EntityManagerFactoryProvider;
import ru.ifmo.se.repository.PersonRepository;
import ru.ifmo.se.security.BasicAuthFilter;
import ru.ifmo.se.service.PersonService;

import java.net.URI;
import java.util.Map;


public class Main {
    public static void main(String[] args) throws Exception {
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

        ResourceConfig config = new ResourceConfig();
        config.register(BasicAuthFilter.class);
        config.register(JacksonJsonProvider.class);
        config.register(new PersonRestController(personService));
        config.register(new PersonServiceExceptionMapper());

        URI url = UriBuilder.fromUri("http://localhost/").port(8080).build();

        HttpServer server = JdkHttpServerFactory.createHttpServer(url, config);

        System.out.println("REST service started at: " + url);
        System.in.read();
        server.stop(0);
    }
}
