package com.example.bank.controllers;

import com.example.bank.entities.DTO.LoginResponseDTO;
import com.example.bank.entities.DTO.PhysicalPersonAuthenticationDTO;
import com.example.bank.entities.models.PhysicalPerson;
import com.example.bank.services.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.net.ssl.SSLEngineResult;

@Controller
@RequestMapping("login")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @PostMapping(value = "/physical-person")
    public ResponseEntity<LoginResponseDTO> physicalLogin(@RequestBody @Valid PhysicalPersonAuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.cpf(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((PhysicalPerson) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
