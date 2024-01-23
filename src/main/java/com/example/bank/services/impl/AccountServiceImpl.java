package com.example.bank.services.impl;

import com.example.bank.entities.DTO.EditPhysicalPersonDTO;
import com.example.bank.entities.DTO.PhysicalPersonDTO;
import com.example.bank.entities.models.Account;
import com.example.bank.entities.models.PhysicalPerson;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.PhysicalPersonRepository;
import com.example.bank.services.AccountService;
import com.example.bank.services.PhysicalPersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account create(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void deposit(UUID accountId, Double value) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            account.get().deposit(value);
            accountRepository.save(account.get());
        }
    }
    @Override
    public void withdraw(UUID accountId, Double value) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            account.get().withdraw(value);
            accountRepository.save(account.get());
        }
    }
}
