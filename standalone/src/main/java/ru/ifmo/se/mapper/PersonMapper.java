package ru.ifmo.se.mapper;

import ru.ifmo.se.model.dto.PersonDto;
import ru.ifmo.se.model.entity.Person;

public class PersonMapper {

    public static PersonDto toDto(Person person) {
        if (person == null) {
            return null;
        }

        return PersonDto.builder()
                .name(person.getName())
                .surname(person.getSurname())
                .age(person.getAge())
                .address(person.getAddress())
                .phoneNumber(person.getPhoneNumber())
                .build();
    }

    public static Person toEntity(PersonDto personDto) {
        if (personDto == null) {
            return null;
        }

        return Person.builder()
                .name(personDto.getName())
                .surname(personDto.getSurname())
                .age(personDto.getAge())
                .address(personDto.getAddress())
                .phoneNumber(personDto.getPhoneNumber())
                .build();
    }
}
