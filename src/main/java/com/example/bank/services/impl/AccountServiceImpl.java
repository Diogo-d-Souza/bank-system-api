package com.example.bank.services.impl;

import com.example.bank.entities.DTO.AccountDTO;
import com.example.bank.entities.DTO.EditPhysicalPersonDTO;
import com.example.bank.entities.DTO.NewBalanceDTO;
import com.example.bank.entities.DTO.PhysicalPersonDTO;
import com.example.bank.entities.models.Account;
import com.example.bank.entities.models.PhysicalPerson;
import com.example.bank.exceptions.NotFoundException;
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Account create(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public AccountDTO findOne(UUID id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.map(value -> modelMapper.map(value, AccountDTO.class)).orElse(null);
    }
    @Override
    public NewBalanceDTO deposit(UUID accountId, Double value) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            account.get().deposit(value);
            accountRepository.save(account.get());
            return new NewBalanceDTO(account.get().getBalance());
        }
        return null;
    }
    @Override
    public NewBalanceDTO withdraw(UUID accountId, Double value) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            account.get().withdraw(value);
            accountRepository.save(account.get());
            return new NewBalanceDTO(account.get().getBalance());
        }
        return null;
    }
}
