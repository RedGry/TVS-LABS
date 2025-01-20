package ru.ifmo.se.command;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class HelpCommand implements CliCommand {
    private final Map<String, CliCommand> commands;

    public HelpCommand(Map<String, CliCommand> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(Scanner scanner, String authHeader) {
        System.out.println("Available commands:");
        AtomicInteger index = new AtomicInteger(1);
        commands.values().forEach(command -> System.out.println(
                index.getAndIncrement() + ". " +
                        command.getName() + " - " +
                        command.getDescription()));
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Show available commands";
    }
}
