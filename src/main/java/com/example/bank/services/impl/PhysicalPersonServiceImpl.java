package com.example.bank.services.impl;

import com.example.bank.entities.DTO.EditPhysicalPersonDTO;
import com.example.bank.entities.DTO.PhysicalPersonDTO;
import com.example.bank.entities.models.Account;
import com.example.bank.entities.models.PhysicalPerson;
import com.example.bank.exceptions.NotFoundException;
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
public class PhysicalPersonServiceImpl implements PhysicalPersonService {

    @Autowired
    private PhysicalPersonRepository physicalPersonRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PhysicalPersonDTO create(PhysicalPerson physicalPerson) {
        Account account = new Account();
        account.setPhysicalPersonId(physicalPerson.getId());
        accountService.create(account);
        physicalPerson.setAccountId(account.getId());
        PhysicalPerson createdPhysicalPerson = physicalPersonRepository.save(physicalPerson);
        account.setPhysicalPersonId(createdPhysicalPerson.getId());
        accountService.create(account);
        return modelMapper.map(createdPhysicalPerson, PhysicalPersonDTO.class);
    }

    @Override
    public List<PhysicalPersonDTO> getAll() {
        List<PhysicalPerson> allCostumers = physicalPersonRepository.findAll();
        List<PhysicalPersonDTO> allCostumersDTO = new ArrayList<PhysicalPersonDTO>();
        for(PhysicalPerson costumer: allCostumers) {
            allCostumersDTO.add(modelMapper.map(costumer, PhysicalPersonDTO.class));
        }
        return allCostumersDTO;
    }

    @Override
    public PhysicalPersonDTO getOne(UUID id) {
        Optional<PhysicalPerson> costumer = physicalPersonRepository.findById(id);
        if (!costumer.isPresent()) throw new NotFoundException("Costumer doesn't exist");
        return costumer.map(physicalPerson -> modelMapper.map(physicalPerson, PhysicalPersonDTO.class)).orElse(null);
    }

    @Override
    public void edit(EditPhysicalPersonDTO editedPerson, UUID id) {
        Optional<PhysicalPerson> person = physicalPersonRepository.findById(id);
        if(person.isPresent()) {
            PhysicalPerson personToBeEdited = person.get();
            personToBeEdited.setName(editedPerson.name());
            personToBeEdited.setEmail(editedPerson.email());
            physicalPersonRepository.save(personToBeEdited);
        }
    }

    @Override
    public void delete(UUID id) {
        Optional<PhysicalPerson> person = physicalPersonRepository.findById(id);
        if(person.isPresent()) {
            PhysicalPerson personToBeDeleted = person.get();
            physicalPersonRepository.delete(personToBeDeleted);
        }
    }

    @Override
    public void deposit(UUID id, Double value) {
        PhysicalPersonDTO physicalPerson = this.getOne(id);
        accountService.deposit(physicalPerson.getAccountID(), value);
    }

    @Override
    public void withdraw(UUID id, Double value) {
        PhysicalPersonDTO physicalPerson = this.getOne(id);
        accountService.withdraw(physicalPerson.getAccountID(), value);
    }
}
