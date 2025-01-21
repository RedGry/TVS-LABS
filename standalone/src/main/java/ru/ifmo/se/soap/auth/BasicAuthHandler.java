package ru.ifmo.se.soap.auth;

import jakarta.xml.soap.SOAPException;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BasicAuthHandler implements SOAPHandler<SOAPMessageContext> {
    private final String encodedAuth;

    public BasicAuthHandler(String encodedAuth) {
        this.encodedAuth = encodedAuth;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean isOutbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (!isOutbound) {
            try {
                QName operationName = (QName) context.get(MessageContext.WSDL_OPERATION);
                String methodName = operationName.getLocalPart();

                if ("searchPersons".equals(methodName) || "findPersonById".equals(methodName)) {
                    return true; // Пропускаем проверку авторизации
                }

                Map<String, List<String>> headers = (Map<String, List<String>>) context.get(MessageContext.HTTP_REQUEST_HEADERS);
                if (headers != null) {
                    List<String> authorizationHeaders = headers.get("Authorization");
                    if (authorizationHeaders != null && !authorizationHeaders.isEmpty()) {
                        String authorization = authorizationHeaders.get(0);
                        System.out.println("Authorization: " + authorization);

                        if (authorization == null || !authorization.startsWith("Basic ")) {
                            throw new SOAPException("Invalid Authorization header");
                        }

                        if (!authorization.equals(encodedAuth)) {
                            throw new SOAPException("Invalid Authorization credentials");
                        }

                        return true;
                    }
                }

                throw new SOAPException("Missing Authorization header");
            } catch (SOAPException e) {
                throw new RuntimeException("Authentication failed: " + e.getMessage(), e);
            }
        }
        return true;
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }
}
