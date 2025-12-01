package com.safetynet.alert.service;

import com.safetynet.alert.model.Firestation;
import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.repository.FirestationRepository;
import com.safetynet.alert.repository.MedicalRecordRepository;
import com.safetynet.alert.repository.PersonRepository;
import com.safetynet.alert.service.dto.FireStationDto;
import com.safetynet.alert.service.dto.FireStationPersonDto;
import org.springframework.stereotype.Service;



import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FirestationService {
    private final FirestationRepository firestationRepository;
    private final PersonRepository personRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    // Définir le format de date une fois comme constante (bonne pratique)
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public FirestationService(FirestationRepository firestationRepository, PersonRepository personRepository, MedicalRecordRepository medicalRecordRepository) {
        this.firestationRepository = firestationRepository;
        this.personRepository = personRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    // --- Méthodes CRUD ---

    public List<Firestation> allFirestations() {
        return firestationRepository.findAllFirestations();
    }

    //POST
    public Firestation addFirestation(Firestation firestation) {
        return firestationRepository.saveFirestation(firestation);
    }

    //PUT
    public Firestation updateFirestation(String address, Firestation firestation) {
        return firestationRepository.updateFirestation(address, firestation);
    }

    //DELETE
    public boolean deleteFirestation(String address) {
        return firestationRepository.deleteFirestation(address);
    }

    // --- Méthode Métier : FireStationDto ---

    //FireStationDto
    public FireStationDto findAllPersonsByStationNumber(int number) { // usage $ curl -XGET :8080/firestation?stationNumber=2
        FireStationDto result = new FireStationDto();
        FireStationPersonDto firestationPerson;
        List<FireStationPersonDto> people = new ArrayList<>(); // 3. Le type de la liste doit être FireStationPersonDto
        result.setPeople(people);

        // get all stations by number
        List<Firestation> firestations = firestationRepository.findAllByStationNumber(number);
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAllMedicalRecords();
        // get all people
        List<Person> persons = personRepository.findAllPersons();

        Integer childsCount = 0;
        Integer adultsCount = 0;

        // compare addresses and add the results in FireStationDto
        for (Person person : persons) {
            Firestation matchingFirestation = fireStationContains(firestations, person); // Correction ici : utilise 'firestations' et non 'person.getFirestations()'
            if (matchingFirestation != null) {
                firestationPerson = new FireStationPersonDto();
                firestationPerson.setFirstName(person.getFirstName());
                firestationPerson.setLastName(person.getLastName());
                firestationPerson.setAddress(person.getAddress());
                firestationPerson.setPhoneNumber(person.getPhone());

                MedicalRecord medicalRecord = medicalRecordContains(medicalRecords, person); // Correction : utilise 'medicalRecords' et non 'person.getMedicalRecords()'
                if (medicalRecord != null) {
                    if ((computeAge(medicalRecord.getBirthdate())  < 18))  {

                        childsCount++;
                    } else { // 4. Ajout de la logique d'incrémentation pour les adultes
                        adultsCount++;
                    }
                }
                result.getPeople().add(firestationPerson); // 5. Utilisation de l'objet créé localement 'firestationPerson'
            }
        }
        result.setAdultsCount(adultsCount);
        result.setChildsCount(childsCount);
        return result;
    }

    /**
     * Calcule l'âge en années à partir d'une chaîne de date de naissance (format MM/dd/yyyy).
     */
    private int computeAge(String birthDate) {
        // Utilisation du formateur statique pour parser la chaîne de date
        LocalDate birth = LocalDate.parse(birthDate, DATE_FORMATTER);
        return Period.between(birth, LocalDate.now()).getYears();
    }

    // --- Méthodes Utilitaire ---

    /**
     * Vérifie si l'adresse d'une personne est couverte par une des casernes de la liste.
     */
    private Firestation fireStationContains(List<Firestation> fireStations, Person person) {
        for (Firestation fireStation : fireStations) {
            if (fireStation.getAddress().equals(person.getAddress()))
                return fireStation;
        }
        return null;
    }

    /**
     * Recherche le MedicalRecord correspondant à la personne (par prénom et nom).
     */
    private MedicalRecord medicalRecordContains(List<MedicalRecord> medicalRecords, Person person) {
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName()
                    .equals(person.getLastName())) {
                return medicalRecord;
            }
        }
        return null;
    }


}