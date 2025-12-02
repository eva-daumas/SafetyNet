package com.safetynet.alert.service;

import com.safetynet.alert.model.Firestation;
import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.repository.FirestationRepository;
import com.safetynet.alert.repository.MedicalRecordRepository;
import com.safetynet.alert.repository.PersonRepository;
import com.safetynet.alert.service.dto.FireDto;
import com.safetynet.alert.service.dto.FireStationDto;
import com.safetynet.alert.service.dto.FireStationPersonDto;
import org.springframework.stereotype.Service;



import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                    if ((computeAge(medicalRecord.getBirthdate()) < 18)) {

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




    private final List<Person> persons = List.of(
            new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"),
            new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "drk@email.com"),
            new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com"),
            new Person("Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"),
            new Person("Felicia", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544", "jaboyd@email.com"),
            new Person("Jonanathan", "Marrack", "29 15th St", "Culver", "97451", "841-874-6513", "drk@email.com"),
            new Person("Tessa", "Carman", "834 Binoc Ave", "Culver", "97451", "841-874-6512", "tenz@email.com"),
            new Person("Peter", "Duncan", "644 Gershwin Cir", "Culver", "97451", "841-874-6512", "jaboyd@email.com"),
            new Person("Foster", "Shepard", "748 Townings Dr", "Culver", "97451", "841-874-6544", "jaboyd@email.com"),
            new Person("Tony", "Cooper", "112 Steppes Pl", "Culver", "97451", "841-874-6874", "tcoop@ymail.com"),
            new Person("Lily", "Cooper", "489 Manchester St", "Culver", "97451", "841-874-9845", "lily@email.com"),
            new Person("Sophia", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7878", "soph@email.com"),
            new Person("Warren", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7512", "ward@email.com"),
            new Person("Zach", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7512", "zarc@email.com"),
            new Person("Reginold", "Walker", "908 73rd St", "Culver", "97451", "841-874-8547", "reg@email.com"),
            new Person("Jamie", "Peters", "908 73rd St", "Culver", "97451", "841-874-7462", "jpeter@email.com"),
            new Person("Ron", "Peters", "112 Steppes Pl", "Culver", "97451", "841-874-8888", "jpeter@email.com"),
            new Person("Allison", "Boyd", "112 Steppes Pl", "Culver", "97451", "841-874-9888", "aly@imail.com"),
            new Person("Brian", "Stelzer", "947 E. Rose Dr", "Culver", "97451", "841-874-7784", "bstel@email.com"),
            new Person("Shawna", "Stelzer", "947 E. Rose Dr", "Culver", "97451", "841-874-7784", "ssanw@email.com"),
            new Person("Kendrik", "Stelzer", "947 E. Rose Dr", "Culver", "97451", "841-874-7784", "bstel@email.com"),
            new Person("Clive", "Ferguson", "748 Townings Dr", "Culver", "97451", "841-874-6741", "clivfd@ymail.com"),
            new Person("Eric", "Cadigan", "951 LoneTree Rd", "Culver", "97451", "841-874-7458", "gramps@email.com")
    );


    // Méthode pour récupérer tous les habitants d'une adresse sous forme de FireDto
    public List<FireDto> getFireDtoByAddress(String address) {
        return persons.stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .map(p -> new FireDto(
                        p.getFirstName(),
                        p.getLastName(),
                        p.getAddress(),
                        p.getPhone(),
                        "",                 // mettre l'âge si disponible
                        new String[]{},     // médicaments si disponibles
                        new String[]{}      // allergies si disponibles
                ))
                .collect(Collectors.toList());
    }


}
