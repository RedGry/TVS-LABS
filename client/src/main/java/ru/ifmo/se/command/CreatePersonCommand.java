package ru.ifmo.se.command;

import ru.ifmo.se.soap.PersonDto;
import ru.ifmo.se.soap.PersonServiceException;
import ru.ifmo.se.soap.PersonWebService;
import ru.ifmo.se.utils.Util;

import java.util.Scanner;

public class CreatePersonCommand implements CliCommand {
    private final PersonWebService personWebService;

    public CreatePersonCommand(PersonWebService personWebService) {
        this.personWebService = personWebService;
    }

    @Override
    public void execute(Scanner scanner) {
        PersonDto personDto = Util.getPersonDtoFromInput(scanner);

        try {
            int id = personWebService.createPerson(personDto);
            System.out.println("Created person with ID: " + id);
        } catch (PersonServiceException e) {
            System.out.println("Error creating person: " + e.getFaultInfo().getMessage());
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
