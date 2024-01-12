package com.example.bank.services.impl;

import com.example.bank.entities.DTO.PhysicalPersonDTO;
import com.example.bank.entities.models.PhysicalPerson;
import com.example.bank.repository.PhysicalPersonRepository;
import com.example.bank.services.PhysicalPersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhysicalPersonServiceImpl implements PhysicalPersonService {

    @Autowired
    private PhysicalPersonRepository physicalPersonRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PhysicalPersonDTO create(PhysicalPerson physicalPerson) {
        PhysicalPerson createdPhysicalPerson = physicalPersonRepository.save(physicalPerson);
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
}
