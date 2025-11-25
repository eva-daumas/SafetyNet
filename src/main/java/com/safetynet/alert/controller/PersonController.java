package com.safetynet.alert.controller;

import ch.qos.logback.core.joran.spi.HttpUtil;
import com.safetynet.alert.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

   private final PersonService personService;

   PersonController(PersonService personService) {
       this.personService = personService;
   }

    @RequestMapping(value = "communityEmail", method = RequestMethod.GET)
    public List<String> listeEmails(@RequestParam(name = "city") String city) {
        return personService.findAllEmailsByCity(city);
    }
    @RequestMapping(value = "phoneAlert", method = RequestMethod.GET)
    public List<String> phoneList(@RequestParam(name = "firestation") String number) {
        return personService.findPhoneByNumber(number);
    }

}
