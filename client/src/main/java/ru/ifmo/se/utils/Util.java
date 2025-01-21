package ru.ifmo.se.utils;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.handler.MessageContext;
import ru.ifmo.se.command.*;
import ru.ifmo.se.soap.PersonDto;
import ru.ifmo.se.soap.PersonService;
import ru.ifmo.se.soap.PersonWebService;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
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

    public static Map<String, CliCommand> produceCommands(String soapUrl, AuthCredentials authCredentials) throws Exception {
        Map<String, CliCommand> commands = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
        objectMapper.setDefaultPrettyPrinter(prettyPrinter);

        URL url = new URL(soapUrl);
        PersonService personService = new PersonService(url);
        PersonWebService personWebServiceProxy = personService.getPersonWebServicePort();

        BindingProvider bindingProvider = (BindingProvider) personWebServiceProxy;
        bindingProvider.getRequestContext().put(
                MessageContext.HTTP_REQUEST_HEADERS,
                Map.of("Authorization", List.of(authCredentials.getEncodedAuth()))
        );
        bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, authCredentials.getUsername());
        bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, authCredentials.getPassword());

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
        PersonDto personDto = new PersonDto();
        System.out.println("Enter person details:");
        for (Field field : PersonDto.class.getDeclaredFields()) {
            field.setAccessible(true);
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
