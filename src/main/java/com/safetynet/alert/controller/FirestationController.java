package com.safetynet.alert.controller;

import com.safetynet.alert.model.Firestation;
import com.safetynet.alert.service.FirestationService;

import org.springframework.http.ResponseEntity;
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

    // POST : Ajouter une nouvelle firestation
    // -------------------------------------------------------
    @PostMapping
    public Firestation addFirestation(@RequestBody Firestation firestation) {
        return firestationService.addFirestation(firestation);
    }

    // -------------------------------------------------------
    // PUT : Modifier une firestation existante par adresse
    // -------------------------------------------------------
    @PutMapping("/{address}")
    public ResponseEntity<Firestation> updateFirestation(
            @PathVariable String address,
            @RequestBody Firestation firestation) {

        Firestation updated = firestationService.updateFirestation(address, firestation);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // -------------------------------------------------------
    // DELETE : Supprimer une firestation par adresse
    // -------------------------------------------------------
    @DeleteMapping("/{address}")
    public ResponseEntity<Void> deleteFirestation(@PathVariable String address) {
        boolean deleted = firestationService.deleteFirestation(address);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}


