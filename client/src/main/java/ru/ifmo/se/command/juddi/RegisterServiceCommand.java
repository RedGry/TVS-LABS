package ru.ifmo.se.command.juddi;

import org.uddi.api_v3.AuthToken;
import org.uddi.api_v3.BindingDetail;
import ru.ifmo.se.command.CliCommand;
import ru.ifmo.se.CliSoapClientApp;
import ru.ifmo.se.juddi.JuddiClient;
import ru.ifmo.se.utils.CommandUtils;

import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class RegisterServiceCommand implements CliCommand {
    private final JuddiClient juddiClient;

    public RegisterServiceCommand(JuddiClient juddiClient) {
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
        if (businessKey == null) {
            System.out.println("Business you specified does not exist! First create it using createBusiness command!");
            return;
        }

        String serviceKey = juddiClient.findServiceInfo(businessKey, CliSoapClientApp.PERSON_SERVICE_NAME);
        String baseUrl = CommandUtils.enterString(
                "Enter PersonService base URL (without name and ?wsdl)",
                "You must enter service base URL!",
                true,
                scanner
        );
        URL serviceUrl = new URL(baseUrl);

        AuthToken authToken = juddiClient.provideAuthToken();
        if (serviceKey == null) {
            serviceKey = juddiClient.registerService(businessKey, CliSoapClientApp.PERSON_SERVICE_NAME, authToken);
            System.out.println("Registered PersonService as service with key " + serviceKey);
        } else {
            System.out.println("Service for PersonService already exists! Will check binding to url...");
        }

        BindingDetail bindingDetail = juddiClient.findServiceBindingInfo(serviceKey);
        if (bindingDetail != null) {
            List<String> urls = JuddiClient.getUrlsFromBinding(bindingDetail);
            if (urls.contains(baseUrl)) {
                System.out.println("Binding for PersonService with the same URL already exist!");
                juddiClient.discardAuthToken(authToken);
                return;
            } else {
                System.out.println("Binding for PersonService exists, but it hase different binding. Will register a new binding.");
            }
        } else {
            System.out.println("No bindings for PersonService exist! Will register a new binding.");
        }

        bindingDetail = juddiClient.bindService(serviceKey, serviceUrl, authToken);
        System.out.println("Successfully created binding for PersonService");
    }

    @Override
    public String getName() {
        return "registerService";
    }

    @Override
    public String getDescription() {
        return "Register PersonService in JUDDI";
    }
}
