package ru.ifmo.se.controller.errors;

public class PersonServiceException extends RuntimeException {
    private final FaultBean faultInfo;

    public PersonServiceException(String message, FaultBean faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public PersonServiceException(String message, FaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    public FaultBean getFaultInfo() {
        return faultInfo;
    }
}
