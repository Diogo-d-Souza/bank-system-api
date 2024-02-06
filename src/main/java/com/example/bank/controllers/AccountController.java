package com.example.bank.controllers;

import com.example.bank.entities.DTO.AccountDTO;
import com.example.bank.services.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "v1/account")
@SecurityRequirement(name = "bearerAuth")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<AccountDTO> getOne(@PathVariable UUID id){
        AccountDTO account = accountService.findOne(id);
        return ResponseEntity.ok().body(account);
    }
}
