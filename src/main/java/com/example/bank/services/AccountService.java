package com.example.bank.services;

import com.example.bank.entities.models.Account;

import java.util.UUID;

public interface AccountService {
    public Account create(Account account);
    public void deposit(UUID accountId, Double value);
}
