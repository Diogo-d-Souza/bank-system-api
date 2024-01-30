package com.example.bank.services;

import com.example.bank.entities.DTO.EditPhysicalPersonDTO;
import com.example.bank.entities.DTO.NewBalanceDTO;
import com.example.bank.entities.DTO.PhysicalPersonDTO;
import com.example.bank.entities.models.PhysicalPerson;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface PhysicalPersonService {
    public PhysicalPersonDTO create(PhysicalPerson physicalPerson);

    public List<PhysicalPersonDTO> getAll();
    public PhysicalPersonDTO getOne(UUID id);

    public void edit(EditPhysicalPersonDTO editedPerson, UUID id);

    public void delete(UUID id);

    public NewBalanceDTO deposit(UUID id, Double value);

    public NewBalanceDTO withdraw(UUID id, Double value);

}
