package com.safetynet.alert.controller;



import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.service.PersonService;
import com.safetynet.alert.service.dto.PersonInfoDto;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    PersonController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "/communityEmail", method = RequestMethod.GET)
    public List<String> listeEmails(@RequestParam(name = "city") String city) {
        return personService.findAllEmailsByCity(city);
    }

    @RequestMapping(value = "/phoneAlert", method = RequestMethod.GET)
    public List<String> phoneList(@RequestParam(name = "firestation") String number) {
        return personService.findPhoneByNumber(number);
    }

    @GetMapping("/personInfo")
    public List<PersonInfoDto> personsList(
            @RequestParam String firstName,
            @RequestParam String lastName) {

        return personService.findAllPersons(firstName, lastName);
    }


    // POST: Ajout d'une personne
    @PostMapping
    public Person addPerson(@RequestBody Person person) {

        return personService.addPerson(person);
    }

    //PUT: Modification d'une personne
    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        Person updated = personService.updatePerson(person);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE : supprimer une personne
    @DeleteMapping
    public ResponseEntity<Void> deletePerson
    (@RequestParam String firstname, @RequestParam String lastname) {
        personService.deletePerson(firstname, lastname);
        return ResponseEntity.ok().build(); // toujours 200 OK }


    }
}




