package com.example.bank.entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountDTO {
    private String agency;
    private String accountNumber;
    private String paymentInstitution;
    private Double balance;
}
