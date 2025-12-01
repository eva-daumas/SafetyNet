package com.safetynet.alert.service.dto;

import lombok.Data;

@Data
public class FireStationPersonDto {
    String lastName;
    String firstName;
    String adress;
    String phoneNumber;

    public void setAddress(String address) {
    }
}
