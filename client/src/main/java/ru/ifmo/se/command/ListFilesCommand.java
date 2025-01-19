package ru.ifmo.se.command;

import ru.ifmo.se.soap.FileService;
import ru.ifmo.se.soap.FileWebService;

import java.util.List;
import java.util.Scanner;

public class ListFilesCommand implements CliCommand {
    private final FileWebService fileService;

    public ListFilesCommand(FileService fileService) {
        this.fileService = fileService.getFileWebServicePort();
    }

    @Override
    public String getName() {
        return "listFiles";
    }

    @Override
    public String getDescription() {
        return "Lists all available files on the SOAP server.";
    }

    @Override
    public void execute(Scanner scanner) {
        try {
            List<String> files = fileService.listFiles();

            if (files.isEmpty()) {
                System.out.println("No files available on the server.");
            } else {
                System.out.println("Available files:");
                for (String file : files) {
                    System.out.println(file);
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving file list: " + e.getMessage());
        }
    }
}
