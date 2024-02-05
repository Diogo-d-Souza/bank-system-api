package com.example.bank.controllers;

import com.example.bank.entities.DTO.LoginResponseDTO;
import com.example.bank.entities.DTO.PhysicalPersonAuthenticationDTO;
import com.example.bank.entities.models.PhysicalPerson;
import com.example.bank.exceptions.NotFoundException;
import com.example.bank.services.security.AuthorizationServiceImpl;
import com.example.bank.services.security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("v1/auth")
public class AuthController {
    @Autowired
    private AuthorizationServiceImpl authorizationService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/refresh-token")
    public ResponseEntity<LoginResponseDTO> refreshToken(HttpServletRequest request){
        return ResponseEntity.ok(authorizationService.refreshToken(request));
    }
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDTO> physicalLogin(@RequestBody @Valid PhysicalPersonAuthenticationDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.cpf(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((PhysicalPerson) auth.getPrincipal());
            var refreshToken = tokenService.generateRefreshToken((PhysicalPerson) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDTO(token, refreshToken));
        } catch (RuntimeException exception) {
            throw new NotFoundException(exception.getMessage());
        }
    }
}
