package com.safetynet.alert.repository;

import com.safetynet.alert.model.MedicalRecord;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    //ChildAlert

    //Methode
    private boolean isUnder18(String birthdate) {
        Date date = null;
        try {
            date = new SimpleDateFormat("DD/MM/YYYY").parse(birthdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = GregorianCalendar.getInstance();
        // a tester / 365.25 System.out.print(LocalDate.parse(birthdate).datesUntil(LocalDate.now()).count());
        return !calendar.getTime().after(date);
    }



    public List<MedicalRecord> findAllMedicalRecordsUnder18() {
        return dataHandler.getData().getMedicalRecords().stream()
                .filter(medicalRecord -> isUnder18(medicalRecord.getBirthdate())).collect(Collectors.toList());
    }

    public MedicalRecord findMedicalWithFirstNameAndLastName(String firstName, String lastName) {
        return dataHandler.getData().getMedicalRecords().stream().filter(medicalRecord ->
                        medicalRecord.getFirstName().equals(firstName))
                .filter(p -> p.getLastName().equals(lastName))
                .findFirst()
                .orElse(new MedicalRecord());
    }

    public List<MedicalRecord> findAllMedicalRecords(String address) {
        return dataHandler.getData().getMedicalRecords();
    }

    public void saveMedicalRecord(MedicalRecord medicalRecord) {

    }
}