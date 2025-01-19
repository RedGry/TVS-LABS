package ru.ifmo.se.command;

import ru.ifmo.se.restclient.PersonDto;
import ru.ifmo.se.restclient.PersonRestClient;
import ru.ifmo.se.utils.Util;

import java.util.Scanner;

public class CreatePersonCommand implements CliCommand {
    private final PersonRestClient personRestClient;

    public CreatePersonCommand(PersonRestClient personRestClient) {
        this.personRestClient = personRestClient;
    }

    @Override
    public void execute(Scanner scanner) {
        PersonDto personDto = Util.getPersonDtoFromInput(scanner);

        try {
            int id = personRestClient.createPerson(personDto);
            System.out.println("Created person with ID: " + id);
        } catch (Exception e) {
            System.out.println("Error creating person: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Create a new person";
    }
}
