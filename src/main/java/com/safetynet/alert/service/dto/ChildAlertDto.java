package com.safetynet.alert.service.dto;

import com.safetynet.alert.model.Person;

import java.util.List;

public class ChildAlertDto {

    private String firstName;
    private String lastName;
    private String age;
    private List<Person> households;

    public ChildAlertDto() {
    }

    public ChildAlertDto(String firstName, String lastName, String age, String households) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.households = households;
    }
    public String getFirstName() { return firstName; }
}
