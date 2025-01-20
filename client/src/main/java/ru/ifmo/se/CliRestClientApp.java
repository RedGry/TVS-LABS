package ru.ifmo.se;

import ru.ifmo.se.command.CliCommand;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Scanner;

import static ru.ifmo.se.utils.Util.produceCommands;

public class CliRestClientApp {
    public static void main(String[] args) {
        String restUrl = System.getenv("REST_SERVICE_URL");
        if (args.length < 2) {
            System.out.println("Usage: java CliRestClientApp <username> <password>");
            return;
        }

        String username = args[0];
        String password = args[1];

        String authHeader = getAuthHeader(username, password);

        if (restUrl == null || restUrl.isEmpty()) {
            System.out.println("REST service URL must be provided via environment variable or command line argument.");
            return;
        }

        try {
            Map<String, CliCommand> commands = produceCommands(restUrl);
            Scanner scanner = new Scanner(System.in);

            System.out.println("Available commands:");
            int index = 1;
            for (CliCommand command : commands.values()) {
                System.out.println(index++ + ". " + command.getName() + " - " + command.getDescription());
            }
            System.out.println("Type the command name or its number to execute it. Type 'clear' to clear the console.");

            while (true) {
                System.out.print("Enter command: ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("clear")) {
                    clearConsole();
                    continue;
                }
                CliCommand command = null;
                try {
                    int commandIndex = Integer.parseInt(input);
                    if (commandIndex > 0 && commandIndex <= commands.size()) {
                        command = (CliCommand) commands.values().toArray()[commandIndex - 1];
                    }
                } catch (NumberFormatException e) {
                    command = commands.get(input);
                }

                if (command != null) {
                    try {
                        command.execute(scanner, authHeader);
                    } catch (Exception e) {
                        System.out.println("Cannot execute command. REST service is not available.");
                    }
                } else {
                    System.out.println("Unknown command. Type 'help' for a list of available commands.");
                }

                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("REST service could not be initialized. Commands are unavailable.");
        }
    }

    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Failed to clear the console.");
        }
    }

    private static String getAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
    }
}
