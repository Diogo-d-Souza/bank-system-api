package com.example.bank.services;

import com.example.bank.entities.DTO.PhysicalPersonDTO;
import com.example.bank.entities.models.PhysicalPerson;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PhysicalPersonService {
    public PhysicalPersonDTO create(PhysicalPerson physicalPerson);
    public List<PhysicalPersonDTO> getAll();
}
