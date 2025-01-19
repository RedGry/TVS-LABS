package ru.ifmo.se.command;

import ru.ifmo.se.soap.FileService;
import ru.ifmo.se.soap.FileWebService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class DownloadFileCommand implements CliCommand {
    private final FileWebService fileService;

    public DownloadFileCommand(FileService fileService) {
        this.fileService = fileService.getFileWebServicePort();
    }

    @Override
    public String getName() {
        return "downloadFile";
    }

    @Override
    public String getDescription() {
        return "Downloads a file from the SOAP service.";
    }

    @Override
    public void execute(Scanner scanner) {
        System.out.print("Enter the file ID to download: ");
        int fileId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter the destination path to save the file: ");
        String destinationPath = scanner.nextLine();

        try {
            byte[] fileData = fileService.downloadFileById(fileId);

            if (fileData != null) {
                String fileName = fileService.getFileNameById(fileId);
                Path destination = Paths.get(destinationPath, fileName);
                Files.write(destination, fileData);

                System.out.println("File downloaded successfully: " + destination);
            } else {
                System.out.println("File not found on the server for ID: " + fileId);
            }
        } catch (Exception e) {
            System.out.println("Error downloading file: " + e.getMessage());
        }
    }

}
