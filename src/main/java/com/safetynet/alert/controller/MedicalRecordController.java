package com.safetynet.alert.controller;

import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.service.MedicalRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    //GET :Recuperation de tous les dossiers
    @GetMapping
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }

    //POST: Ajout d'un dossier medical
    @PostMapping
    public MedicalRecord addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.addMedicalRecord(medicalRecord);
    }

    //PUT: Mise à jour dossier medical
    @PutMapping
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        MedicalRecord updated = medicalRecordService.updateMedicalRecord(medicalRecord);
        return ResponseEntity.ok(updated);
    }

    // DELETE : supprimer un dossier médical existant
    @DeleteMapping public ResponseEntity<Void> deleteMedicalRecord
    ( @RequestParam String firstname, @RequestParam String lastname) {
        medicalRecordService.deleteMedicalRecord(firstname, lastname);
        return ResponseEntity.ok().build(); // toujours 200 OK }


    }
}



