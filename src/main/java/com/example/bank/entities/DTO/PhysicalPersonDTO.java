package com.example.bank.entities.DTO;

import com.example.bank.entities.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;
import java.util.UUID;

@Data
public class PhysicalPersonDTO {
    private UUID id;
    @NotBlank
    private String name;
    @Email
    private String email;
    @CPF
    private String cpf;
    @NotBlank
    private String rg;
    private Date birthDate;
    @NotBlank
    private UserRole role;
    private UUID accountID;
}
