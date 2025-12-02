package com.safetynet.alert.controller;

import com.safetynet.alert.model.Firestation;
import com.safetynet.alert.service.FirestationService;

import com.safetynet.alert.service.dto.FireDto;
import com.safetynet.alert.service.dto.FireStationDto;
import com.safetynet.alert.service.dto.FireStationPersonDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class FirestationController {
    private final FirestationService firestationService;

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    @GetMapping("firestation")
    public List<Firestation> allFirestations() {

        return this.firestationService.allFirestations();
    }

    //FireStationDto
    @RequestMapping(value = "/fireStation", method = RequestMethod.GET)
    public FireStationDto personsListByFireStation(@RequestParam(name = "stationNumber") int number) {
        return this.firestationService.findAllPersonsByStationNumber(number);
    }


    //FireDto
    @GetMapping("fire")
    public List<FireDto> getFireDtoByAddress(@RequestParam String address) {
        return firestationService.getFireDtoByAddress(address);
    }



    // POST : Ajouter une nouvelle firestation
    // -------------------------------------------------------
    @PostMapping("firestation")
    public Firestation addFirestation(@RequestBody Firestation firestation) {
        return firestationService.addFirestation(firestation);
    }

    // -------------------------------------------------------
    // PUT : Modifier une firestation existante par adresse
    // -------------------------------------------------------
    @PutMapping("firestation/{address}")
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
    @DeleteMapping("firestation/{address}")
    public ResponseEntity<Void> deleteFirestation(@PathVariable String address) {
        boolean deleted = firestationService.deleteFirestation(address);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}


