package ru.ifmo.se.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ifmo.se.soap.Person;
import ru.ifmo.se.soap.PersonWebService;

import java.util.Scanner;

public class FindPersonByIdCommand implements CliCommand {
    private final PersonWebService personWebService;
    private final ObjectMapper objectMapper;

    public FindPersonByIdCommand(PersonWebService personWebService, ObjectMapper objectMapper) {
        this.personWebService = personWebService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void execute(Scanner scanner) {
        System.out.print("Enter person ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // очистка буфера

        try {
            Person person = personWebService.findPersonById(id);
            if (person == null) {
                System.out.println("Person with ID " + id + " not found.");
                return;
            }

            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(person);
            System.out.println("Found person: ");
            System.out.println(json);
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
