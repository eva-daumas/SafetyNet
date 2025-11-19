    package com.safetynet.alert.service;

    import com.safetynet.alert.model.Person;
    import com.safetynet.alert.repository.PersonRepository;
    import org.springframework.stereotype.Service;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    public class PersonService {

        private final PersonRepository personRepository;

        // Spring injecte le repository via le constructeur
        public PersonService(PersonRepository personRepository) {
            this.personRepository = personRepository;
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
    }
