package ru.ifmo.se.command;

import java.util.Scanner;

public interface CliCommand {
    void execute(Scanner scanner);
    String getName();
    String getDescription();
}
