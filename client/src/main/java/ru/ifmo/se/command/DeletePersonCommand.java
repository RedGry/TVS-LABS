package ru.ifmo.se.command;

import ru.ifmo.se.soap.PersonWebService;

import java.util.Scanner;

public class DeletePersonCommand implements CliCommand {
    private final PersonWebService personWebService;

    public DeletePersonCommand(PersonWebService personWebService) {
        this.personWebService = personWebService;
    }

    @Override
    public void execute(Scanner scanner) {
        System.out.print("Enter person ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            boolean success = personWebService.deletePersonById(id);
            if (success) {
                System.out.println("Person deleted successfully.");
            } else {
                System.out.println("Person with ID " + id + " not found.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting person: " + e.getMessage());
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