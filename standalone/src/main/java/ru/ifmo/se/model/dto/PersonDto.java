package ru.ifmo.se.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
    private String name;
    private String surname;
    private int age;
    private String address;
    private String phoneNumber;
}
