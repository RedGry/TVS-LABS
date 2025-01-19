package ru.ifmo.se.restclient;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PersonDto {
    private int id;
    private String name;
    private String surname;
    private int age;
    private String address;
    private String phoneNumber;
}