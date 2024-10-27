package ru.ifmo.se.command;

import ru.ifmo.se.soap.Person;
import ru.ifmo.se.soap.PersonDto;
import ru.ifmo.se.soap.PersonServiceException;
import ru.ifmo.se.soap.PersonWebService;
import ru.ifmo.se.utils.Util;

import java.util.Scanner;

public class UpdatePersonCommand implements CliCommand {
    private final PersonWebService personWebService;

    public UpdatePersonCommand(PersonWebService personWebService) {
        this.personWebService = personWebService;
    }

    @Override
    public void execute(Scanner scanner) {
        int id = Util.getIntInput(scanner, "Enter person ID to update: ", -1);

        try {
            Person existingPerson = personWebService.findPersonById(id);
            if (existingPerson == null) {
                System.out.println("Person with ID " + id + " not found. Cannot perform update.");
                return;
            }

            System.out.println("Person found. Enter new details to update.");
            PersonDto updatedPersonDto = Util.getPersonDtoFromInput(scanner);

            boolean success = personWebService.updatePerson(id, updatedPersonDto);
            if (success) {
                System.out.println("Person updated successfully.");
            } else {
                System.out.println("Failed to update person.");
            }
        } catch (PersonServiceException e) {
            System.out.println("Error updating person: " + e.getFaultInfo().getMessage());
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
