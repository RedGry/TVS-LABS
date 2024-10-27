package ru.ifmo.se.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import ru.ifmo.se.model.dto.PersonDto;
import ru.ifmo.se.model.dto.PersonListRequestDto;
import ru.ifmo.se.model.entity.Person;
import ru.ifmo.se.service.PersonService;

import java.util.Collections;
import java.util.List;

@WebService(serviceName = "PersonService")
public class PersonWebService {
    private final PersonService personService;

    public PersonWebService(PersonService personService) {
        this.personService = personService;
    }

    @WebMethod
    public List<Person> searchPersons(@WebParam(name = "arg0") PersonListRequestDto personListRequestDto) {
        if (personListRequestDto == null) {
            System.out.println("Received null PersonListRequestDto");
            return Collections.emptyList();
        }

        int limit = personListRequestDto.getLimit() != null ? personListRequestDto.getLimit() : 10;
        int offset = personListRequestDto.getOffset() != null ? personListRequestDto.getOffset() : 0;

        return personService.searchPersons(personListRequestDto.getQuery(), limit, offset);
    }

    @WebMethod
    public Person findPersonById(@WebParam(name = "id") int id) {
        return personService.readPerson(id);
    }

    @WebMethod
    public int createPerson(@WebParam(name = "personDto") PersonDto personDto) {
        return personService.createPerson(personDto);
    }

    @WebMethod
    public boolean updatePerson(@WebParam(name = "id") int id, @WebParam(name = "personDto") PersonDto personDto) {
        return personService.updatePerson(id, personDto);
    }

    @WebMethod
    public boolean deletePersonById(@WebParam(name = "id") int id) {
        return personService.deletePersonById(id);
    }
}
