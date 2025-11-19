package com.safetynet.alert.controller;

import com.safetynet.alert.model.Firestation;
import com.safetynet.alert.service.FirestationService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FirestationController {
    private final FirestationService firestationService;

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    @GetMapping("firestation")
    public List<Firestation>allFirestation(){
    return this.firestationService.allFirestation();
}


}
