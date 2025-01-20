package ru.ifmo.se.command;

import ru.ifmo.se.restclient.PersonDto;
import ru.ifmo.se.restclient.PersonRestClient;
import ru.ifmo.se.utils.Util;

import java.util.Scanner;

public class UpdatePersonCommand implements CliCommand {
    private final PersonRestClient personRestClient;

    public UpdatePersonCommand(PersonRestClient personRestClient) {
        this.personRestClient = personRestClient;
    }

    @Override
    public void execute(Scanner scanner, String authHeader) {
        int id = Util.getIntInput(scanner, "Enter person ID to update: ", -1);

        try {
            PersonDto existingPerson = personRestClient.findPersonById(id);
            if (existingPerson == null) {
                System.out.println("Person with ID " + id + " not found. Cannot perform update.");
                return;
            }

            System.out.println("Person found. Enter new details to update.");
            PersonDto updatedPersonDto = Util.getPersonDtoFromInput(scanner);

            boolean success = personRestClient.updatePerson(id, updatedPersonDto, authHeader);
            if (success) {
                System.out.println("Person updated successfully.");
            } else {
                System.out.println("Failed to update person.");
            }
        } catch (Exception e) {
            System.out.println("Error updating person: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "Update an existing person by ID";
    }
}
