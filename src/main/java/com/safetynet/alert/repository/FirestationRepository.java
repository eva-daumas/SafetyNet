package com.safetynet.alert.repository;


import com.safetynet.alert.model.Firestation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirestationRepository {
    private final DataHandler dataHandler;

    public FirestationRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public List<Firestation>findAllFirestation() {
        return dataHandler.getData().getFirestation();
    }
}
