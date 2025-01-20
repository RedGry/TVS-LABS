package ru.ifmo.se.command;

import java.util.Scanner;

public interface CliCommand {
    void execute(Scanner scanner, String authHeader);
    String getName();
    String getDescription();
}
