package ru.ifmo.se.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ifmo.se.soap.Person;
import ru.ifmo.se.soap.PersonListRequestDto;
import ru.ifmo.se.soap.PersonWebService;
import ru.ifmo.se.utils.Util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Scanner;

public class FilterPersonCommand implements CliCommand {
    private final PersonWebService personWebService;
    private final ObjectMapper objectMapper;

    public FilterPersonCommand(PersonWebService personWebService, ObjectMapper objectMapper) {
        this.personWebService = personWebService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void execute(Scanner scanner) {
        try {
            System.out.println("Available fields for filtering Person:");
            for (Field field : Person.class.getDeclaredFields()) {
                System.out.println("- " + field.getName() + ": " + field.getType().getSimpleName());
            }
            System.out.println();
            System.out.println("Filtering rules:");
            System.out.println("- Use AND/OR to combine multiple conditions");
            System.out.println("- Use '(', ')' to group");
            System.out.println("- Use comparison operators (e.g., =, >, <, >=, <=) for fields");
            System.out.println();

            System.out.print("Enter filter query (e.g., 'age>30 AND name=Кеша'): ");
            String query = scanner.nextLine();

            int limit = Util.getIntInput(scanner, "Enter limit (default 10): ", 10);
            int offset = Util.getIntInput(scanner, "Enter offset (default 0): ", 0);

            PersonListRequestDto personListRequestDto = new PersonListRequestDto();
            personListRequestDto.setLimit(limit);
            personListRequestDto.setOffset(offset);
            personListRequestDto.setQuery(query);

            List<Person> filteredPersons = personWebService.searchPersons(personListRequestDto);
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(filteredPersons);
            System.out.println(json);
        } catch (Exception e) {
            System.out.println("Error filtering persons: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "search";
    }

    @Override
    public String getDescription() {
        return "Filter persons based on a query with optional limit and offset";
    }
}