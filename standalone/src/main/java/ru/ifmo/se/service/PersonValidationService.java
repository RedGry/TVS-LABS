package ru.ifmo.se.service;

import ru.ifmo.se.model.dto.PersonDto;
import ru.ifmo.se.soap.errors.FaultBean;
import ru.ifmo.se.soap.errors.PersonServiceException;

public class PersonValidationService {
    private static final String PHONE_REGEX = "^\\+?[0-9]{10,15}$";

    public static void validatePersonDto(PersonDto personDto) throws PersonServiceException {
        StringBuilder errorMessageBuilder = new StringBuilder();

        if (personDto == null) {
            throw new PersonServiceException("PersonDto is null", new FaultBean("Provided PersonDto is null"));
        }
        if (personDto.getName() == null || personDto.getName().isEmpty()) {
            errorMessageBuilder.append("Name field cannot be empty. ");
        }
        if (personDto.getSurname() == null) {
            errorMessageBuilder.append("Surname field cannot be empty. ");
        }
        if (personDto.getAge() < 0) {
            errorMessageBuilder.append("Age must be a non-negative integer. ");
        }
        if (personDto.getPhoneNumber() != null && !personDto.getPhoneNumber().matches(PHONE_REGEX)) {
            errorMessageBuilder.append("Phone number must match the format: +79996665544. ");
        }
        if (!errorMessageBuilder.isEmpty()) {
            throw new PersonServiceException(
                    "Validation failed for PersonDto",
                    new FaultBean(errorMessageBuilder.toString().trim())
            );
        }
    }
}
