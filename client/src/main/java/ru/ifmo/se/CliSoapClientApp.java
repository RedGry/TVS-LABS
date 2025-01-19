package ru.ifmo.se;

import ru.ifmo.se.command.CliCommand;
import ru.ifmo.se.utils.ProxyPool;

import java.util.Map;
import java.util.Scanner;

import static ru.ifmo.se.utils.Util.produceCommands;

public class CliSoapClientApp {
    public static void main(String[] args) {
        String soapUrl = System.getenv("SOAP_SERVICE_URL");
        if (args.length > 0) {
            soapUrl = args[0];
        }

        if (soapUrl == null || soapUrl.isEmpty()) {
            System.out.println("SOAP service URL must be provided via environment variable or command line argument.");
            return;
        }

        try {
            // Создаем пул прокси-объектов
            ProxyPool proxyPool = new ProxyPool(soapUrl, 5);

            // Генерируем команды с учетом пула прокси
            Map<String, CliCommand> commands = produceCommands(proxyPool);
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
                        command.execute(scanner);
                    } catch (Exception e) {
                        System.out.println("Cannot execute command. SOAP service is not available.");
                    }
                } else {
                    System.out.println("Unknown command. Type 'help' for a list of available commands.");
                }

                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("SOAP service could not be initialized. Commands are unavailable.");
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
}