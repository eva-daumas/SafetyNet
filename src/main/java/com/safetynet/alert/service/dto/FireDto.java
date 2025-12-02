package com.safetynet.alert.service.dto;

import lombok.Data;

@Data
public class FireDto {
    String lastName;
    String firstName;
    String address;
    String phoneNumber;
    String age;
    String[] medications;
    String[] allergies;

    public FireDto(String firstName, String lastName, String address, String phoneNumber,
                   String age, String[] medications, String[] allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }

}
