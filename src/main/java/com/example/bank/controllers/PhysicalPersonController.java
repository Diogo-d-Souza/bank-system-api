package com.example.bank.controllers;

import com.example.bank.entities.DTO.EditPhysicalPersonDTO;
import com.example.bank.entities.DTO.NewBalanceDTO;
import com.example.bank.entities.DTO.PhysicalPersonDTO;
import com.example.bank.entities.DTO.Transactions;
import com.example.bank.entities.models.PhysicalPerson;
import com.example.bank.exceptions.NotFoundException;
import com.example.bank.exceptions.ResponseErrorMessage;
import com.example.bank.services.PhysicalPersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "v1/physical-person")
@SecurityRequirement(name = "bearerAuth")
public class PhysicalPersonController {
    @Autowired
    private PhysicalPersonService physicalPersonService;
    @Autowired
    private PasswordEncoder encoder;

    @Operation(description = "Get all the physical person created")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return all the physical persons"),
            @ApiResponse(responseCode = "400", description = "No physical person registered")
    })
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PhysicalPersonDTO>> getAll() {
        var allCostumers = physicalPersonService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(allCostumers);
    }

    @Operation(description = "Get one physical person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return a physical person"),
            @ApiResponse(responseCode = "404", description = "No physical person registered with this ID")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PhysicalPersonDTO> get(@PathVariable UUID id) {
        var costumer = physicalPersonService.getOne(id);
        return ResponseEntity.status(HttpStatus.OK).body(costumer);
    }

    @Operation(description = "Create a physical person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Physical person created"),
            @ApiResponse(responseCode = "400", description = "CPF/Email already exists"),
            @ApiResponse(responseCode = "409", description = "CPF/Email already exists")
    })
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<PhysicalPersonDTO> create(@RequestBody @Valid PhysicalPerson physicalPerson) {
        physicalPerson.setPassword(encoder.encode(physicalPerson.getPassword()));
        var createdCustomer = physicalPersonService.create(physicalPerson);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @Operation(description = "Edit a physical person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Physical person edited successfully"),
            @ApiResponse(responseCode = "400", description = "Email invalid"),
            @ApiResponse(responseCode = "404", description = "No physical person registered with this ID"),
            @ApiResponse(responseCode = "409", description = "Email already exist")
    })
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> edit(@RequestBody @Valid EditPhysicalPersonDTO editPhysicalPersonDTO, @PathVariable UUID id) {
        physicalPersonService.edit(editPhysicalPersonDTO, id);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Delete a physical person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Physical person deleted successfully"),
            @ApiResponse(responseCode = "400", description = "UUID invalid"),
            @ApiResponse(responseCode = "404", description = "No physical person registered with this ID"),
    })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        physicalPersonService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Deposit a value into a physical person account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deposit done successfully"),
            @ApiResponse(responseCode = "404", description = "No physical person registered with this ID"),
    })
    @RequestMapping(value = "/deposit/{id}", method = RequestMethod.POST)
    public ResponseEntity<NewBalanceDTO> deposit(@PathVariable UUID id, @RequestBody Transactions transactions) {
        NewBalanceDTO newBalanceDTO = physicalPersonService.deposit(id, transactions.value());
        return ResponseEntity.ok().body(newBalanceDTO);

    }

    @Operation(description = "Deposit a value into a physical person account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deposit done successfully"),
            @ApiResponse(responseCode = "404", description = "No physical person registered with this ID"),
            @ApiResponse(responseCode = "406", description = "Insufficient balance to make a withdraw")
    })
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
}
