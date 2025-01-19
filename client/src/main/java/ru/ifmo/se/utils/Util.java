package ru.ifmo.se.utils;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ifmo.se.command.*;
import ru.ifmo.se.soap.*;

import java.lang.reflect.Field;
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

    public static Map<String, CliCommand> produceCommands(ProxyPool proxyPool) throws Exception {
        Map<String, CliCommand> commands = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
        objectMapper.setDefaultPrettyPrinter(prettyPrinter);

        URL url = new URL("http://localhost:8080/FileService");
        FileService fileService = new FileService(url);

        ExitCommand exitCommand = new ExitCommand();
        commands.put(exitCommand.getName(), exitCommand);

        FilterPersonCommand filterPersonCommand = new FilterPersonCommand(proxyPool, objectMapper);
        commands.put(filterPersonCommand.getName(), filterPersonCommand);

        FindPersonByIdCommand findPersonByIdCommand = new FindPersonByIdCommand(proxyPool, objectMapper);
        commands.put(findPersonByIdCommand.getName(), findPersonByIdCommand);

        CreatePersonCommand createPersonCommand = new CreatePersonCommand(proxyPool);
        commands.put(createPersonCommand.getName(), createPersonCommand);

        UpdatePersonCommand updatePersonCommand = new UpdatePersonCommand(proxyPool);
        commands.put(updatePersonCommand.getName(), updatePersonCommand);

        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(proxyPool);
        commands.put(deletePersonCommand.getName(), deletePersonCommand);

        HelpCommand helpCommand = new HelpCommand(commands);
        commands.put(helpCommand.getName(), helpCommand);

        UploadFileCommand uploadFileCommand = new UploadFileCommand(fileService);
        commands.put(uploadFileCommand.getName(), uploadFileCommand);

        DownloadFileCommand downloadFileCommand = new DownloadFileCommand(fileService);
        commands.put(downloadFileCommand.getName(), downloadFileCommand);

        DeleteFileCommand deleteFileCommand = new DeleteFileCommand(fileService);
        commands.put(deleteFileCommand.getName(), deleteFileCommand);

        ListFilesCommand listFilesCommand = new ListFilesCommand(fileService);
        commands.put(listFilesCommand.getName(), listFilesCommand);

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
