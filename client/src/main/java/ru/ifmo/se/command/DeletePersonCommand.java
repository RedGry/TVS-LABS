package ru.ifmo.se.command;

import ru.ifmo.se.soap.PersonWebService;
import ru.ifmo.se.utils.Util;

import java.util.Scanner;

public class DeletePersonCommand implements CliCommand {
    private final PersonWebService personWebService;

    public DeletePersonCommand(PersonWebService personWebService) {
        this.personWebService = personWebService;
    }

    @Override
    public void execute(Scanner scanner) {
        int id = Util.getIntInput(scanner, "Enter person ID to delete: ", -1);

        try {
            boolean success = personWebService.deletePersonById(id);
            if (success) {
                System.out.println("Person deleted successfully.");
            } else {
                System.out.println("Person with ID " + id + " not found.");
            }
        } catch (Exception e) {
            System.out.println("Unexpected error deleting person: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "Delete a person by ID";
    }
}