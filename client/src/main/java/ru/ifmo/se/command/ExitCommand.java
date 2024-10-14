package ru.ifmo.se.command;

import java.util.Scanner;

public class ExitCommand implements CliCommand {

    @Override
    public void execute(Scanner scanner) {
        System.out.println("Exiting application.");
        System.exit(0);
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "Exit the application";
    }
}
