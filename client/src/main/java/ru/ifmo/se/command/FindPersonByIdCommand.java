package ru.ifmo.se.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ifmo.se.soap.Person;
import ru.ifmo.se.utils.ProxyPool;
import ru.ifmo.se.utils.Util;

import java.util.Scanner;

public class FindPersonByIdCommand implements CliCommand {
    private final ProxyPool proxyPool;
    private final ObjectMapper objectMapper;

    public FindPersonByIdCommand(ProxyPool proxyPool, ObjectMapper objectMapper) {
        this.proxyPool = proxyPool;
        this.objectMapper = objectMapper;
    }

    @Override
    public void execute(Scanner scanner) {
        int id = Util.getIntInput(scanner, "Enter person ID: ", -1);

        try {
            var personWebService = proxyPool.acquireProxy();
            try {
                Person person = personWebService.findPersonById(id);
                if (person == null) {
                    System.out.println("Person with ID " + id + " not found.");
                    return;
                }

                String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(person);
                System.out.println("Found person: ");
                System.out.println(json);
            } finally {
                proxyPool.releaseProxy(personWebService);
            }
        } catch (Exception e) {
            System.out.println("Error finding person by ID: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "findById";
    }

    @Override
    public String getDescription() {
        return "Find person by ID";
    }
}
