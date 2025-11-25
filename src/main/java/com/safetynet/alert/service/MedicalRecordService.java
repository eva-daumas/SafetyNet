package com.safetynet.alert.service;

import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    // GET : Récupérer tous les dossiers médicaux
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.getAllMedicalRecords();
    }

    // POST : Ajouter un dossier médical
    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordRepository.addMedicalRecord(medicalRecord);
        return medicalRecord;
    }

    // PUT : Mettre à jour un dossier médical existant
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.updateMedicalRecord(medicalRecord);
    }

    // Supprimer un dossier médical
    public void deleteMedicalRecord(String firstname, String lastname) {
        List<MedicalRecord> allRecords = medicalRecordRepository.getAllMedicalRecords();
        for (MedicalRecord record : allRecords) {
            if (record.getFirstName().equals(firstname) && record.getLastName().equals(lastname)) {
                allRecords.remove(record);
            }
            break; // supprime seulement le premier dossier trouvé
        }
    }
}





