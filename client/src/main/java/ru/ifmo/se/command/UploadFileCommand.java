package ru.ifmo.se.command;

import ru.ifmo.se.soap.FileService;
import ru.ifmo.se.soap.FileWebService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Scanner;

public class UploadFileCommand implements CliCommand {
    private final FileWebService fileService;

    public UploadFileCommand(FileService fileService) {
        this.fileService = fileService.getFileWebServicePort();
    }

    @Override
    public String getName() {
        return "uploadFile";
    }

    @Override
    public String getDescription() {
        return "Uploads a file to the SOAP service.";
    }

    @Override
    public void execute(Scanner scanner) {
        System.out.print("Enter the file path to upload: ");
        String filePath = scanner.nextLine();

        try {
            Path path = Paths.get(filePath);
            byte[] fileData = Files.readAllBytes(path);
            String fileName = path.getFileName().toString();
            String response = fileService.uploadFile(fileName, fileData);

            System.out.println("Response: " + response);
        } catch (Exception e) {
            System.out.println("Error uploading file: " + e.getMessage());
        }
    }
}