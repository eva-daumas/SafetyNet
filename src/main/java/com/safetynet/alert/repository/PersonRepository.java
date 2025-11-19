package com.safetynet.alert.repository;

import com.safetynet.alert.model.Firestation;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.service.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository

public class PersonRepository {

    private final DataHandler dataHandler;

 public PersonRepository(DataHandler dataHandler) {
     this.dataHandler = dataHandler;
 }

   public List<Person> findAllPersons() {
        return dataHandler.getData().getPersons();
    }
}
