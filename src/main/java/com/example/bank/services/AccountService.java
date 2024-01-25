package com.example.bank.services;

import com.example.bank.entities.DTO.AccountDTO;
import com.example.bank.entities.models.Account;

import java.util.UUID;

public interface AccountService {
    public Account create(Account account);
    public AccountDTO findOne(UUID id);
    public void deposit(UUID accountId, Double value);
    public void withdraw(UUID accountId, Double value);
}
