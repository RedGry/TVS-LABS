package ru.ifmo.se.soap.errors;

import jakarta.xml.ws.WebFault;
import lombok.Getter;

@Getter
@WebFault(name = "PersonServiceFault")
public class PersonServiceException extends Exception {
    private final FaultBean faultInfo;

    public PersonServiceException(String message, FaultBean faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public PersonServiceException(String message, FaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }
}
