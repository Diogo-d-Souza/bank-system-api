package com.example.bank.entities.models;

import com.example.bank.exceptions.InsufficientFoundsException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_account")
public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotBlank
    private String agency = "0001";
    private String accountNumber = "3776232-13";
    @NotBlank
    private String paymentInstitution = "Didas Payment";
    @NotNull
    private Double balance = 0.0;
    private UUID physicalPersonId;

    public void deposit(Double value) {
        this.balance = this.balance + value;
    }
    public void withdraw(Double value) {

        double newBalance = this.balance - value;
        if (newBalance < 0) {
            throw new InsufficientFoundsException("Your balance is insufficient");
        } else {
            this.balance = newBalance;
        }
    }
}
