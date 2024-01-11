package com.example.bank.services;

import com.example.bank.entities.DTO.PhysicalPersonDTO;
import com.example.bank.entities.models.PhysicalPerson;

public interface PhysicalPersonService {
    public PhysicalPersonDTO create(PhysicalPerson physicalPerson);
}
