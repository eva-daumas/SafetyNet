package com.safetynet.alert.repository;

import com.safetynet.alert.model.MedicalRecord;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MedicalRecordRepository {

    private final DataHandler dataHandler;

    // Constructeur : dépend seulement du DataHandler
    public MedicalRecordRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    // Retourne la liste de tous les dossiers médicaux
    public List<MedicalRecord> getAllMedicalRecords() {
        return dataHandler.getData().getMedicalRecords();
    }

    // POST Ajouter un dossier médical à la liste
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        getAllMedicalRecords().add(medicalRecord);
    }

    // PUT Mettre à jour un dossier médical existant
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
        for (MedicalRecord record : getAllMedicalRecords()) {
            if (record.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName().trim())
                    && record.getLastName().equalsIgnoreCase(medicalRecord.getLastName().trim())) {
                record.setBirthdate(medicalRecord.getBirthdate());
                record.setMedications(medicalRecord.getMedications());
                record.setAllergies(medicalRecord.getAllergies());
                return record;
            }
        }
        return null; // null si non trouvé
    }

    // DELETE Supprimer un dossier médical existant
    public boolean deleteMedicalRecord(String firstName, String lastName) {
        List<MedicalRecord> records = getAllMedicalRecords();
        return records.removeIf(record ->
                record.getFirstName().equalsIgnoreCase(firstName.trim()) &&
                        record.getLastName().equalsIgnoreCase(lastName.trim())
        );
    }

    // Recherche un dossier médical par prénom et nom
    public MedicalRecord find(String firstName, String lastName) {
        for (MedicalRecord record : getAllMedicalRecords()) {
            if (record.getFirstName().equalsIgnoreCase(firstName.trim()) &&
                    record.getLastName().equalsIgnoreCase(lastName.trim())) {
                return record;
            }
        }
        return null; // non trouvé
    }



}
