package com.example.bank.controllers;

import com.example.bank.entities.DTO.LoginResponseDTO;
import com.example.bank.entities.DTO.PhysicalPersonAuthenticationDTO;
import com.example.bank.entities.models.PhysicalPerson;
import com.example.bank.exceptions.NotFoundException;
import com.example.bank.services.security.AuthorizationServiceImpl;
import com.example.bank.services.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
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

    @Operation(description = "Login a existent user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged successfully"),
            @ApiResponse(responseCode = "404", description = "User does not exist")
    })
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDTO> physicalLogin(@RequestBody @Valid PhysicalPersonAuthenticationDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.cpf(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((PhysicalPerson) auth.getPrincipal());
            var refreshToken = tokenService.generateRefreshToken((PhysicalPerson) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDTO(token, refreshToken));
        } catch (RuntimeException exception) {
            throw new NotFoundException("User does not exist");
        }
    }
}
