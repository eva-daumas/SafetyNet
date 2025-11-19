package com.safetynet.alert.service;

import com.safetynet.alert.model.Firestation;
import com.safetynet.alert.repository.FirestationRepository;
import com.safetynet.alert.repository.MedicalRecordRepository;
import com.safetynet.alert.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirestationService {
    private final FirestationRepository firestationRepository;

    public FirestationService(FirestationRepository firestationRepository) {
        this.firestationRepository = firestationRepository;

    }

    public List<Firestation>allFirestation() {
        return firestationRepository.findAllFirestation();
    }
}
