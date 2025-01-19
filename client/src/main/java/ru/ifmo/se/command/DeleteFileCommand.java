package ru.ifmo.se.command;

import ru.ifmo.se.soap.FileService;
import ru.ifmo.se.soap.FileWebService;

import java.util.Scanner;

public class DeleteFileCommand implements CliCommand {
    private final FileWebService fileService;

    public DeleteFileCommand(FileService fileService) {
        this.fileService = fileService.getFileWebServicePort();
    }

    @Override
    public String getName() {
        return "deleteFile";
    }

    @Override
    public String getDescription() {
        return "Deletes a file from the SOAP server by its ID.";
    }

    @Override
    public void execute(Scanner scanner) {
        System.out.print("Enter the file ID to delete: ");
        int fileId = scanner.nextInt();
        scanner.nextLine(); // очистка буфера

        try {
            String response = fileService.deleteFileById(fileId);
            System.out.println(response);
        } catch (Exception e) {
            System.out.println("Error deleting file: " + e.getMessage());
        }
    }
}
