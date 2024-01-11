package com.example.bank.repository;

import com.example.bank.entities.models.PhysicalPerson;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhysicalPersonRepository extends JpaRepository<PhysicalPerson, UUID> {
}
