package ru.ifmo.se;

import ru.ifmo.se.command.CliCommand;
import ru.ifmo.se.utils.Util;

import java.net.URL;
import java.util.Map;
import java.util.Scanner;

import static ru.ifmo.se.utils.Util.produceCommands;

public class CliSoapClientApp {
    public final static String PERSON_SERVICE_NAME = "PersonService";
    public final static String TVS_COMPANY_NAME = "TVS";

    public static boolean SOAP_URL_SELECTED = false;
    public static boolean SOAP_COMMANDS_PRODUCED = false;
    public static URL SOAP_URL = null;

    public static void main(String[] args) throws Exception {
        if (args.length < 4) {
            System.out.println("Usage: java -jar <jar-file-name>.jar <juddi-host> <juddi-port> <juddi-username> <juddi-password>");
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);

        String juddHost = args[0];
        String juddiPort = args[1];
        String juddiUser = args[2];
        String juddiPassword = args[3];

        Map<String, CliCommand> commands = Util.produceJuddiCommands(juddHost, juddiPort, juddiUser, juddiPassword);
        System.out.println("Welcome to PersonService selection! Enter command to proceed. Use 'help' for help.");

        try {

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

                if (SOAP_URL_SELECTED && !SOAP_COMMANDS_PRODUCED) {
                    System.out.println("Welcome to Person SOAP Service! Enter command to proceed. Use 'help' for help.");
                    commands = produceCommands(SOAP_URL.toString());
                    SOAP_COMMANDS_PRODUCED = true;
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
