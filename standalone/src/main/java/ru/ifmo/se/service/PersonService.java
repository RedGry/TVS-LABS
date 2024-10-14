package ru.ifmo.se.service;

import ru.ifmo.se.model.entity.Person;
import ru.ifmo.se.repository.PersonRepository;

import java.util.List;

public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> searchPersons(String query, int limit, int offset) {
        return personRepository.findPerson(query, limit, offset);
    }
}
