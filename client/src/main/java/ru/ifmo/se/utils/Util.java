package ru.ifmo.se.utils;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ifmo.se.command.*;
import ru.ifmo.se.soap.PersonDto;
import ru.ifmo.se.soap.PersonService;
import ru.ifmo.se.soap.PersonWebService;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Util {
    public static int getIntInput(Scanner scanner, String prompt, int defaultValue) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                return defaultValue;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public static Map<String, CliCommand> produceCommands(String soapUrl) throws Exception {
        Map<String, CliCommand> commands = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
        objectMapper.setDefaultPrettyPrinter(prettyPrinter);

        URL url = new URL(soapUrl);
        PersonService personService = new PersonService(url);
        PersonWebService personWebServiceProxy = personService.getPersonWebServicePort();

        ExitCommand exitCommand = new ExitCommand();
        commands.put(exitCommand.getName(), exitCommand);

        FilterPersonCommand filterPersonCommand = new FilterPersonCommand(personWebServiceProxy, objectMapper);
        commands.put(filterPersonCommand.getName(), filterPersonCommand);

        FindPersonByIdCommand findPersonByIdCommand = new FindPersonByIdCommand(personWebServiceProxy, objectMapper);
        commands.put(findPersonByIdCommand.getName(), findPersonByIdCommand);

        CreatePersonCommand createPersonCommand = new CreatePersonCommand(personWebServiceProxy);
        commands.put(createPersonCommand.getName(), createPersonCommand);

        UpdatePersonCommand updatePersonCommand = new UpdatePersonCommand(personWebServiceProxy);
        commands.put(updatePersonCommand.getName(), updatePersonCommand);

        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(personWebServiceProxy);
        commands.put(deletePersonCommand.getName(), deletePersonCommand);

        HelpCommand helpCommand = new HelpCommand(commands);
        commands.put(helpCommand.getName(), helpCommand);

        return commands;
    }

    public static PersonDto getPersonDtoFromInput(Scanner scanner) {
        System.out.println("Enter person details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Surname: ");
        String surname = scanner.nextLine();
        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine();

        PersonDto personDto = new PersonDto();
        personDto.setName(name);
        personDto.setSurname(surname);
        personDto.setAge(age);
        personDto.setAddress(address);
        personDto.setPhoneNumber(phoneNumber);

        return personDto;
    }
}
