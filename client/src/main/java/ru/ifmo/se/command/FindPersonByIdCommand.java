package ru.ifmo.se.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ifmo.se.restclient.PersonDto;
import ru.ifmo.se.restclient.PersonRestClient;
import ru.ifmo.se.utils.Util;

import java.util.Scanner;

public class FindPersonByIdCommand implements CliCommand {
    private final PersonRestClient personRestClient;
    private final ObjectMapper objectMapper;

    public FindPersonByIdCommand(PersonRestClient personRestClient, ObjectMapper objectMapper) {
        this.personRestClient = personRestClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public void execute(Scanner scanner) {
        int id = Util.getIntInput(scanner, "Enter person ID: ", -1);

        try {
            PersonDto person = personRestClient.findPersonById(id);
            if (person == null) {
                System.out.println("Person with ID " + id + " not found.");
                return;
            }

            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(person);
            System.out.println("Found person: ");
            System.out.println(json);
        } catch (Exception e) {
            System.out.println("Error finding person by ID: " + id);
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
