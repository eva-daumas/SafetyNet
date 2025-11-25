package com.safetynet.alert.controller;

import com.safetynet.alert.model.Firestation;
import com.safetynet.alert.service.FirestationService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("firestation")
public class FirestationController {
    private final FirestationService firestationService;

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    @GetMapping
    public List<Firestation> allFirestations() {
        return this.firestationService.allFirestations();
    }


}
