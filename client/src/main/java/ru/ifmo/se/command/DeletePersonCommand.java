package ru.ifmo.se.command;

import ru.ifmo.se.utils.ProxyPool;
import ru.ifmo.se.utils.Util;

import java.util.Scanner;

public class DeletePersonCommand implements CliCommand {
    private final ProxyPool proxyPool;

    public DeletePersonCommand(ProxyPool proxyPool) {
        this.proxyPool = proxyPool;
    }

    @Override
    public void execute(Scanner scanner) {
        int id = Util.getIntInput(scanner, "Enter person ID to delete: ", -1);

        try {
            var personWebService = proxyPool.acquireProxy();
            try {
                boolean success = personWebService.deletePersonById(id);
                if (success) {
                    System.out.println("Person deleted successfully.");
                } else {
                    System.out.println("Person with ID " + id + " not found.");
                }
            } finally {
                proxyPool.releaseProxy(personWebService);
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