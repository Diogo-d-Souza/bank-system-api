package com.example.bank.services;

import com.example.bank.entities.DTO.AccountDTO;
import com.example.bank.entities.DTO.NewBalanceDTO;
import com.example.bank.entities.models.Account;

import java.util.UUID;

public interface AccountService {
    public Account create(Account account);
    public AccountDTO findOne(UUID id);
    public NewBalanceDTO deposit(UUID accountId, Double value);
    public NewBalanceDTO withdraw(UUID accountId, Double value);
}
