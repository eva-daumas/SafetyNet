package com.safetynet.alert.repository;


import com.safetynet.alert.model.Firestation;
import com.safetynet.alert.model.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class FirestationRepository {
    private final DataHandler dataHandler;

    public FirestationRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public List<Firestation> findAllFirestation() {
        return dataHandler.getData().getFirestation();
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

    //FireStationDto
    public List<Firestation> findAllByStationNumber(Integer number) { // 3 usages
        return dataHandler.getData().getFirestations().stream()
                .filter(fireStation -> fireStation.getStation().
                        equals(number.toString())).collect(Collectors.toList());
    }

    public Firestation findFireStationByNumberAddress(String address) { // 1 usage 4 p.826
        return dataHandler.getData().getFirestations().stream().filter(fireStation ->
                        fireStation.getAddress().equals(address))
                .findFirst()
                .orElseGet(() -> new Firestation());
    }
}



