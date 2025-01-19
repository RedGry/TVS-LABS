package ru.ifmo.se.service;

import ru.ifmo.se.mapper.PersonMapper;
import ru.ifmo.se.model.dto.PersonDto;
import ru.ifmo.se.model.entity.Person;
import ru.ifmo.se.repository.PersonRepository;

import java.util.List;

public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> searchPersons(String query, int limit, int offset){
        return personRepository.findPerson(query, limit, offset);
    }

    public Person readPerson(int id) {
        return personRepository.readPerson(id);
    }

    public int createPerson(PersonDto personDto) {
        Person person = PersonMapper.toEntity(personDto);
        return personRepository.createPerson(person);
    }

    public boolean updatePerson(int id, PersonDto personDto) {
        Person person = PersonMapper.toEntity(personDto);
        return personRepository.updatePerson(id, person);
    }

    public boolean deletePersonById(int id) {
        return personRepository.deletePersonById(id);
    }
}
