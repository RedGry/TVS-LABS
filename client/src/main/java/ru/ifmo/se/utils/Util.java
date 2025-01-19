package ru.ifmo.se.utils;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import ru.ifmo.se.command.*;
import ru.ifmo.se.restclient.PersonDto;
import ru.ifmo.se.restclient.PersonRestClient;

import java.lang.reflect.Field;
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

    public static Map<String, CliCommand> produceCommands(String restUrl) {
        Map<String, CliCommand> commands = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
        objectMapper.setDefaultPrettyPrinter(prettyPrinter);

        Client restClient = ClientBuilder.newClient().register(JacksonJsonProvider.class);
        PersonRestClient personRestClient = new PersonRestClient(restUrl, restClient);

        ExitCommand exitCommand = new ExitCommand();
        commands.put(exitCommand.getName(), exitCommand);

        FilterPersonCommand filterPersonCommand = new FilterPersonCommand(personRestClient, objectMapper);
        commands.put(filterPersonCommand.getName(), filterPersonCommand);

        FindPersonByIdCommand findPersonByIdCommand = new FindPersonByIdCommand(personRestClient, objectMapper);
        commands.put(findPersonByIdCommand.getName(), findPersonByIdCommand);

        CreatePersonCommand createPersonCommand = new CreatePersonCommand(personRestClient);
        commands.put(createPersonCommand.getName(), createPersonCommand);

        UpdatePersonCommand updatePersonCommand = new UpdatePersonCommand(personRestClient);
        commands.put(updatePersonCommand.getName(), updatePersonCommand);

        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(personRestClient);
        commands.put(deletePersonCommand.getName(), deletePersonCommand);

        HelpCommand helpCommand = new HelpCommand(commands);
        commands.put(helpCommand.getName(), helpCommand);

        return commands;
    }

    public static PersonDto getPersonDtoFromInput(Scanner scanner) {
        PersonDto personDto = new PersonDto();
        System.out.println("Enter person details:");
        for (Field field : PersonDto.class.getDeclaredFields()) {
            field.setAccessible(true);

            if (field.getName().equals("id")) {
                continue;
            }

            System.out.print(field.getName() + " (" + field.getType().getSimpleName() + "): ");
            try {
                if (field.getType() == String.class) {
                    String input = scanner.nextLine();
                    field.set(personDto, input);

                } else if (field.getType() == int.class) {
                    while (true) {
                        try {
                            int input = Integer.parseInt(scanner.nextLine());
                            field.set(personDto, input);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.print("Invalid format. Please enter a valid integer for " + field.getName() + ": ");
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                System.out.println("Error setting value for field " + field.getName() + ": " + e.getMessage());
            }
        }
        return personDto;
    }
}
