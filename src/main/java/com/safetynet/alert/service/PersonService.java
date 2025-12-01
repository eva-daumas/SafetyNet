package com.safetynet.alert.service;

import com.safetynet.alert.model.Firestation;
import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.repository.FirestationRepository;
import com.safetynet.alert.repository.MedicalRecordRepository;
import com.safetynet.alert.repository.PersonRepository;
import com.safetynet.alert.service.dto.ChildAlertDto;
import com.safetynet.alert.service.dto.PersonInfoDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final FirestationRepository firestationRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    // Spring injecte le repository via le constructeur
    public PersonService(PersonRepository personRepository, FirestationRepository firestationRepository, MedicalRecordRepository medicalRecordRepository) {
        this.personRepository = personRepository;
        this.firestationRepository = firestationRepository;
        this.medicalRecordRepository = medicalRecordRepository;
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
        // Liste pour stocker les numéros de téléphone correspondant à la station
        List<String> phones = new ArrayList<>();
        // Récupération de tous les habitants
        List<Person> persons = personRepository.findAllPersons();

        // Récupération de toutes les firestations
        List<Firestation> firestations = firestationRepository.findAllFirestation();

        // Liste pour stocker uniquement les firestations correspondant au numéro recherché
        List<Firestation> sortedFirestation = new ArrayList<>();

        // Parcours de toutes les firestations
        for (Firestation firestation : firestations) {

            // Si le numéro de station correspond à celui recherché
            if (firestation.getStation().equals(number)) {

                // Ajout de cette firestation à la liste filtrée
                sortedFirestation.add(firestation);
            }
        }

        //FOR-EACH IMBRIQUER NESTED
        // Parcours des habitants et des firestations filtrées pour récupérer les téléphones
        for (Person person : persons) {
            for (Firestation firestation : sortedFirestation) {
                // Si l'adresse de la personne correspond à l'adresse de la firestation
                if (person.getAddress().equals(firestation.getAddress())) {
                    // Ajouter le téléphone à la liste des résultats
                    phones.add(person.getPhone());
                }
            }
        }
        // Retourne la liste finale des numéros de téléphone
        return phones;
    }

    // AJOUT D'UNE PERSONNE
    public Person addPerson(Person person) {
        personRepository.addPerson(person);
        return person;
    }

    public Person updatePerson(Person personToUpdate) {
        for (Person p : personRepository.findAllPersons()) {
            if (p.getFirstName().equals(personToUpdate.getFirstName()) &&
                    p.getLastName().equals(personToUpdate.getLastName())) {

                p.setAddress(personToUpdate.getAddress());
                p.setCity(personToUpdate.getCity());
                p.setZip(personToUpdate.getZip());
                p.setPhone(personToUpdate.getPhone());
                p.setEmail(personToUpdate.getEmail());

                return p; // retourne l'objet mis à jour
            }
        }
        return null; // null si personne non trouvée
    }

    // Supprimer une personne
    public void deletePerson(String firstname, String lastname) {
        List<Person> allPerson = personRepository.getAllPerson();
        for (Person person : allPerson) {
            if (person.getFirstName().equals(firstname) && person.getLastName().equals(lastname)) {
                allPerson.remove(person);
            }
            break; // supprime seulement le premier dossier trouvé
        }
    }


    //PersonInfo:
    public List<PersonInfoDto> findAllPersons(String firstName, String lastName) {
        List<PersonInfoDto> result = new ArrayList<>();
        List<Person> persons = personRepository.findAllPersons();

        for (Person p : persons) {
            if (p.getFirstName().equalsIgnoreCase(firstName)
                    && p.getLastName().equalsIgnoreCase(lastName)) {

                PersonInfoDto dto = new PersonInfoDto();
                dto.setLastName(p.getLastName());
                dto.setEmail(p.getEmail());
                dto.setAddress(p.getAddress());

                // Récupération depuis les dossiers médicaux
                MedicalRecord record = medicalRecordRepository.find(p.getFirstName(), p.getLastName());

                if (record != null) {
                    if (record.getBirthdate() != null && !record.getBirthdate().isEmpty()) {
                        String[] parts = record.getBirthdate().split("/"); // "MM/dd/yyyy"
                        int year = Integer.parseInt(parts[2]);
                        int currentYear = java.time.LocalDate.now().getYear();
                        dto.setAge(String.valueOf(currentYear - year));
                    }
                    dto.setMedications(record.getMedications());
                    dto.setAllergies(record.getAllergies());
                }


                result.add(dto);
            }
        }


        return result;
    }

    // -------------------- CHILD ALERT --------------------
    public List<ChildAlertDto> findChildsUnder18ByAddress(String address) {
        List<ChildAlertDto> result = new ArrayList<>();

        // 1️⃣ Récupérer toutes les personnes à cette adresse
        List<Person> personsAtAddress = personRepository.findAllpersonByAddress(address);

        // 2️⃣ Boucle sur chaque personne pour vérifier l'âge
        for (Person person : personsAtAddress) {
            // Récupérer le medical record
            MedicalRecord record = medicalRecordRepository.findMedicalWithFirstNameAndLastName(
                    person.getFirstName(), person.getLastName()
            );

            if (record != null) {
                int age = computeAge(record.getBirthdate());
                if (age < 18) { // C'est un mineur
                    ChildAlertDto dto = new ChildAlertDto();
                    dto.setFirstName(person.getFirstName());
                    dto.setLastName(person.getLastName());
                    dto.setAge(String.valueOf(age));

                    // Ajouter les autres membres du foyer
                    List<Person> householdMembers = personsAtAddress.stream()
                            .filter(p -> !(p.getFirstName().equalsIgnoreCase(person.getFirstName())
                                    && p.getLastName().equalsIgnoreCase(person.getLastName())))
                            .collect(Collectors.toList());
                    dto.setHouseholds(householdMembers);

                    result.add(dto);
                }
            }
        }

        return result;
    }

    // Méthode auxiliaire pour retrouver le medical record d'une personne
    private MedicalRecord medicalRecordsContainsPerson(List<MedicalRecord> medicalRecords, Person person) {
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getFirstName().equalsIgnoreCase(person.getFirstName())
                    && medicalRecord.getLastName().equalsIgnoreCase(person.getLastName())) {
                return medicalRecord;
            }
        }
        return null;
    }

    // Calcul de l'âge à partir de la date de naissance
    private int computeAge(String birthdateOfPerson) {
        LocalDate dob = LocalDate.parse(birthdateOfPerson, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate currentDate = LocalDate.now();
        return Period.between(dob, currentDate).getYears();
    }
}


