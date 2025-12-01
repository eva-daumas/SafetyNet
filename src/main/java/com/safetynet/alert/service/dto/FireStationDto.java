package com.safetynet.alert.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class FireStationDto {
    Integer adultsCount;
    Integer childsCount;
    List<FireStationPersonDto> people;




}
