package ru.ifmo.se.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.soap.MTOM;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@MTOM
@WebService(serviceName = "FileService")
public class FileWebService {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    @WebMethod
    public String uploadFile(String fileName, byte[] data) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO files (name, data) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, fileName);
                statement.setBytes(2, data);
                statement.executeUpdate();
            }
            return "File uploaded successfully: " + fileName;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error uploading file: " + e.getMessage();
        }
    }

    @WebMethod
    public List<String> listFiles() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT id, name FROM files";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                List<String> files = new ArrayList<>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    files.add(String.format("ID: %d, Name: %s", id, name));
                }
                return files;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @WebMethod
    public byte[] downloadFile(String fileName) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT data FROM files WHERE name = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, fileName);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getBytes("data");
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public byte[] downloadFileById(int fileId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT data FROM files WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, fileId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getBytes("data");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @WebMethod
    public String getFileNameById(int fileId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT name FROM files WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, fileId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("name");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @WebMethod
    public String deleteFileById(int fileId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "DELETE FROM files WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, fileId);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    return "File with ID " + fileId + " has been deleted successfully.";
                } else {
                    return "File with ID " + fileId + " does not exist.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error deleting file: " + e.getMessage();
        }
    }
}
