package ru.ifmo.se.command.juddi;

import org.apache.commons.collections.CollectionUtils;
import org.uddi.api_v3.BindingDetail;
import ru.ifmo.se.CliSoapClientApp;
import ru.ifmo.se.command.CliCommand;
import ru.ifmo.se.juddi.JuddiClient;
import ru.ifmo.se.utils.CommandUtils;

import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class LookupServiceCommand implements CliCommand {
    private final JuddiClient juddiClient;

    public LookupServiceCommand(JuddiClient juddiClient) {
        this.juddiClient = juddiClient;
    }

    @Override
    public void execute(Scanner scanner) throws Exception {
        String businessName = CommandUtils.enterString(
                "Enter business name in which you will find service",
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
        if (serviceKey == null) {
            System.out.println("Business found, but looks like PersonService is not registered for business " + businessName + "! Register it using registerService.");
            return;
        }

        BindingDetail bindingDetail = juddiClient.findServiceBindingInfo(serviceKey);
        if (bindingDetail == null) {
            System.out.println("PersonService is found for business " + businessName + " but there are no bindings! Register it using registerService.");
            return;
        }

        List<String> urls = JuddiClient.getUrlsFromBinding(bindingDetail);
        if (CollectionUtils.isEmpty(urls)) {
            System.out.println("PersonService is found for business " + businessName + " but there are no bindings! Register it using registerService.");
            return;
        }

        System.out.println("Found " + urls.size() + " bindings for PersonService! Choose service by using its number or enter 0 to use another command:");
        for (int i = 0; i < urls.size(); i++) {
            System.out.println((i + 1) + ". " + urls.get(i));
        }

        while (true) {
            String str = scanner.nextLine();
            int serviceNum;
            try {
                serviceNum = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                System.out.println("You must enter a number!");
                continue;
            }

            if (serviceNum == 0) {
                System.out.println("Service not selected. Enter another command.");
                break;
            }

            if (serviceNum > urls.size()) {
                System.out.println("You must enter a number less than " + urls.size() + "!");
                continue;
            } else if (serviceNum < 1) {
                System.out.println("You must enter a number greater or equal to 1!");
                continue;
            }

            String urlStr = urls.get(serviceNum - 1);
            System.out.println("Selected service with URL: " + urlStr);
            CliSoapClientApp.SOAP_URL = new URL(urlStr + "/PersonService?wsdl");
            CliSoapClientApp.SOAP_URL_SELECTED = true;
            break;
        }
    }

    @Override
    public String getName() {
        return "lookupService";
    }

    @Override
    public String getDescription() {
        return "Lookups PersonService and allows to select its url.";
    }
}
