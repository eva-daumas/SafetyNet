package com.safetynet.alert.service;

import com.safetynet.alert.model.Firestation;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.repository.FirestationRepository;

import com.safetynet.alert.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FirestationService {
    private final FirestationRepository firestationRepository;
    private final PersonRepository personRepository;
   // private final MedicalRecordRepository medicalRecordRepository;

    public FirestationService(FirestationRepository firestationRepository, PersonRepository personRepository) {
        this.firestationRepository = firestationRepository;
        this.personRepository = personRepository;
      //  this.medicalRecordRepository = medicalRecordRepository;

    }


    public List<Firestation> allFirestations() {
        return firestationRepository.findAllFirestations();
    }


}
