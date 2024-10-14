package ru.ifmo.se.soap;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import ru.ifmo.se.model.dto.PersonListRequestDto;
import ru.ifmo.se.model.entity.Person;
import ru.ifmo.se.service.PersonService;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
@WebService(serviceName = "PersonService")
public class PersonWebService {
    @Inject
    PersonService personService;

    public PersonWebService() {
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
}
