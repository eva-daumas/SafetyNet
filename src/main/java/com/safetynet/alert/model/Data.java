package com.safetynet.alert.model;

import java.util.List;

public class Data {

    private List<Person> persons;
    private List<Firestation> firestations;
    private List<MedicalRecord> medicalrecords;

    public List<Person> getPersons() {

        return persons;
    }

    public void setPerson(List<Person> person) {
        this.persons = person;
    }

    public List<Firestation> getFirestation() {
        return firestations;
    }

    public void setFirestations(List<Firestation> firestations) {
        this.firestations = firestations;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalrecords;
    }

    public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
        this.medicalrecords = medicalRecords;
    }

    public List<Firestation> getFirestations() {
        return firestations;
    }
}
