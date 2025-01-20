package ru.ifmo.se.command.juddi;

import org.uddi.api_v3.AuthToken;
import ru.ifmo.se.command.CliCommand;
import ru.ifmo.se.juddi.JuddiClient;
import ru.ifmo.se.utils.CommandUtils;

import java.util.Scanner;

public class RegisterBusinessCommand implements CliCommand {
    private final JuddiClient juddiClient;

    public RegisterBusinessCommand(JuddiClient juddiClient) {
        this.juddiClient = juddiClient;
    }

    @Override
    public void execute(Scanner scanner) throws Exception {
        String businessName = CommandUtils.enterString(
                "Enter business name in which you will register service",
                "You must enter business name!",
                true,
                scanner
        );

        String businessKey = juddiClient.findBusinessInfo(businessName);
        if (businessKey != null) {
            System.out.println("Business with name" + businessName + " already exists!");
            return;
        }

        AuthToken aToken = juddiClient.provideAuthToken();
        businessKey = juddiClient.registerBusiness(businessName, aToken);
        juddiClient.discardAuthToken(aToken);

        System.out.println("Successfully registered business with name" + businessName + " with key " + businessKey);
    }

    @Override
    public String getName() {
        return "registerBusiness";
    }

    @Override
    public String getDescription() {
        return "Register business in JUDDI";
    }
}
