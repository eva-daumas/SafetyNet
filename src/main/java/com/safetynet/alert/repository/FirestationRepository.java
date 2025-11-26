package com.safetynet.alert.repository;


import com.safetynet.alert.model.Firestation;
import com.safetynet.alert.model.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

@Component
public class FirestationRepository {
    private final DataHandler dataHandler;

    public FirestationRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public List<Firestation> findAllFirestation() {
        return dataHandler.getData().getFirestation();
    }

    public List<Firestation> findPhoneByNumber(String number) {
        List<Firestation> result = new ArrayList<>();

        // On récupère toutes les firestations du JSON
        List<Firestation> stations = dataHandler.getData().getFirestation();

        // On les parcourt
        for (Firestation station : stations) {

            // Si le numéro correspond, on ajoute
            if (station.getStation().equals(number)) {
                result.add(station);
            }
        }

        return result;
    }


    public List<Firestation> findAllFirestations() {
        return dataHandler.getData().getFirestations();
    }

    //POST
    public Firestation saveFirestation(Firestation firestation) {
        dataHandler.getData().getFirestations().add(firestation);
        return firestation;
    }

    //PUT
    public Firestation updateFirestation(String address, Firestation firestation) {
        List<Firestation> stations = dataHandler.getData().getFirestations();
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).getAddress().equals(address)) {
                stations.set(i, firestation);
                return firestation;
            }
        }
        return null;
    }

    //DELETE
    public boolean deleteFirestation(String address) {
        return dataHandler.getData().getFirestations()
                .removeIf(f -> f.getAddress().equals(address));
    }
}



