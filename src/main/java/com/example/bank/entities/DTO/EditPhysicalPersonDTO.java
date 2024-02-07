package com.example.bank.entities.DTO;

import jakarta.validation.constraints.Email;

public record EditPhysicalPersonDTO(@Email(message = "Invalid Email") String email, String name) {
}
