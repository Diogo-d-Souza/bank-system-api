package com.example.bank.entities.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalPersonDTO {
    @NotBlank
    private String name;
    @Email
    private String email;
    @CPF
    private String cpf;
    @NotBlank
    private String rg;
    private Date birthDate;
}
