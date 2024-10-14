package ru.ifmo.se.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ru.ifmo.se.model.entity.Person;
import ru.ifmo.se.repository.PersonRepository;

import java.util.List;

@ApplicationScoped
public class PersonService {

    @Inject
    PersonRepository personRepository;

    public PersonService() {

    }

    public List<Person> searchPersons(String query, int limit, int offset) {
        return personRepository.findPerson(query, limit, offset);
    }
}
