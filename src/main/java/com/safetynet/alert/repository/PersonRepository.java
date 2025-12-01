package com.safetynet.alert.repository;

import com.safetynet.alert.model.Firestation;
import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.service.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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


    // Méthode utilisée pour POST et DELETE
    public List<Person> getAllPerson() {
        return dataHandler.getData().getPersons();
    }

    // POST : ajouter une personne
    public void addPerson(Person person) {
        getAllPerson().add(person);
    }

    // DELETE Supprimer une personne
    public boolean deletePerson(String firstName, String lastName) {
        List<Person> records = getAllPerson();
        return records.removeIf(record ->
                record.getFirstName().equalsIgnoreCase(firstName.trim()) &&
                        record.getLastName().equalsIgnoreCase(lastName.trim())
        );
    }


    //ChildAlert
    public List<Person> findAllpersonByAddress(String address) {
    return dataHandler.getData().getPersons()
            .stream().filter(p->p.getAddress()
            .equals(address)).collect(Collectors.toList());
}

public Person findPersonByFirstNameAndLastName(String firstName, String lastName) {
    return dataHandler.getData().getPersons().stream().filter(person
                    ->person.getFirstName().equals(firstName))
            .filter(p->p.getLastName().equals(lastName))
            .findFirst()
            .orElseGet(() -> new Person());
}

public void savePerson(Person person) {
    dataHandler.getData().getPersons().add(person);
}
}
