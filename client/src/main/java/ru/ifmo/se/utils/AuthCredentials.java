package ru.ifmo.se.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Base64;

@Data
@AllArgsConstructor
public class AuthCredentials {
    private String username;
    private String password;

    public String getEncodedAuth() {
        String credentials = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
