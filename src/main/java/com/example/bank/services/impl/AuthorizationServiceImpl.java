package com.example.bank.services.impl;

import com.example.bank.repository.PhysicalPersonRepository;
import com.example.bank.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements UserDetailsService, AuthorizationService {

    @Autowired
    private PhysicalPersonRepository physicalPersonRepository;
    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        return physicalPersonRepository.findByCpf(cpf);
    }
}
