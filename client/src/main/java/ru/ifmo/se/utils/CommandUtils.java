package ru.ifmo.se.utils;

import java.util.Scanner;

public class CommandUtils {
    public static String enterString(String messageToEnter, String errorMessage, boolean required, Scanner scanner) {
        System.out.println(messageToEnter);
        while (true) {
            String s = scanner.nextLine();
            if (required && (s == null || s.isEmpty())) {
                System.out.println(errorMessage);
            } else {
                return s;
            }
        }
    }
}
