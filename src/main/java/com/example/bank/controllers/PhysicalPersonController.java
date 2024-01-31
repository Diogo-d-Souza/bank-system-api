package com.example.bank.controllers;

import com.example.bank.entities.DTO.EditPhysicalPersonDTO;
import com.example.bank.entities.DTO.NewBalanceDTO;
import com.example.bank.entities.DTO.PhysicalPersonDTO;
import com.example.bank.entities.DTO.Transactions;
import com.example.bank.entities.models.PhysicalPerson;
import com.example.bank.exceptions.NotFoundException;
import com.example.bank.services.PhysicalPersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/physical-person")
public class PhysicalPersonController {
    @Autowired
    private PhysicalPersonService physicalPersonService;
    @Autowired
    private PasswordEncoder encoder;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PhysicalPersonDTO>> getAll() {
        var allCostumers = physicalPersonService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(allCostumers);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PhysicalPersonDTO> get(@PathVariable UUID id) {
        var costumer = physicalPersonService.getOne(id);
        return ResponseEntity.status(HttpStatus.OK).body(costumer);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<PhysicalPersonDTO> create(@RequestBody @Valid PhysicalPerson physicalPerson) {
        physicalPerson.setPassword(encoder.encode(physicalPerson.getPassword()));

        var createdCustomer = physicalPersonService.create(physicalPerson);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);


    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> edit(@RequestBody @Valid EditPhysicalPersonDTO editPhysicalPersonDTO, @PathVariable UUID id) {
        physicalPersonService.edit(editPhysicalPersonDTO, id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        physicalPersonService.delete(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/deposit/{id}", method = RequestMethod.POST)
    public ResponseEntity<NewBalanceDTO> deposit(@PathVariable UUID id, @RequestBody Transactions transactions) {
        NewBalanceDTO newBalanceDTO = physicalPersonService.deposit(id, transactions.value());
        return ResponseEntity.ok().body(newBalanceDTO);

    }

    @RequestMapping(value = "/withdraw/{id}", method = RequestMethod.POST)
    public ResponseEntity<NewBalanceDTO> withdraw(@PathVariable UUID id, @RequestBody Transactions transactions) {
        NewBalanceDTO newBalanceDTO = physicalPersonService.withdraw(id, transactions.value());
        return ResponseEntity.ok().body(newBalanceDTO);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> validationExceptionHandler(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public Map<String, String> uniqueExceptionHandler(DataIntegrityViolationException exception) {
//        Map<String, String> errors = new HashMap<>();
//        exception.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return errors;
//    }
}
