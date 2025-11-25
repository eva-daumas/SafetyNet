    package com.safetynet.alert.service;

    import com.safetynet.alert.model.Firestation;
    import com.safetynet.alert.model.Person;
    import com.safetynet.alert.repository.FirestationRepository;
    import com.safetynet.alert.repository.PersonRepository;
    import org.springframework.stereotype.Service;

    import java.util.ArrayList;
    import java.util.List;

    @Service
    public class PersonService {

        private final PersonRepository personRepository;
        private final FirestationRepository firestationRepository;

        // Spring injecte le repository via le constructeur
        public PersonService(PersonRepository personRepository, FirestationRepository firestationRepository) {
            this.personRepository = personRepository;
            this.firestationRepository = firestationRepository;
        }


        // public List<Person> allPersons() {
        //      return personRepository.findAll();
        //  }


        public List<String> findAllEmailsByCity(String city) {
            List<String> emails = new ArrayList<>();
            List<Person> persons = personRepository.findAllPersons();
            for (Person person : persons) {
                if (person.getCity().equals(city)) {
                    emails.add(person.getEmail());
                }
            }
            return emails;
        }


        public List<String> findPhoneByNumber(String number) {
            //Création liste phones et persons
            List<String> phones = new ArrayList<>();
            List<Person> persons = personRepository.findAllPersons();

            //Recherche toutes les firestations
            List<Firestation> firestations = firestationRepository.findAllFirestation();

            //Création d'une liste firestations peuplée
            List<Firestation> sortedFirestation = new ArrayList<>();

            //Boucle
            for (Firestation firestation : firestations) {

                //Si égalité confirmée au number
                if (firestation.getStation().equals(number)) {

                    //Peuple la liste Firestation
                    sortedFirestation.add(firestation);
                }
            }

            //FOR-EACH IMBRIQUER
            for (Person person : persons) {
             for (Firestation firestation : sortedFirestation) {
             if (person.getAddress().equals(firestation.getAddress())) {
              phones.add(person.getPhone());
        }
        }
        }

            return phones;
        }
    }


