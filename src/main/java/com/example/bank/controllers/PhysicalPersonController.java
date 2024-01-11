package com.example.bank.controllers;

import com.example.bank.entities.DTO.PhysicalPersonDTO;
import com.example.bank.entities.models.PhysicalPerson;
import com.example.bank.services.PhysicalPersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/physical-person")
public class PhysicalPersonController {
    @Autowired
    private PhysicalPersonService physicalPersonService;
    @Autowired
    private PasswordEncoder encoder;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<PhysicalPersonDTO> create(@RequestBody @Valid PhysicalPerson physicalPerson) {
        physicalPerson.setPassword(encoder.encode(physicalPerson.getPassword()));
        System.out.println(physicalPerson.getPassword());
        var createdCustomer = physicalPersonService.create(physicalPerson);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }
}
