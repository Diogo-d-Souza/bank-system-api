package com.example.bank.entities.models;

import com.example.bank.entities.enums.AccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_account")
public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotBlank
    private String branch;
    @NotBlank
    private String accountNumber;
    @NotBlank
    private String paymentInstitution;
    @NotBlank
    private AccountType accountType;

}
