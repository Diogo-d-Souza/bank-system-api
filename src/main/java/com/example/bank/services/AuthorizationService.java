package com.example.bank.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthorizationService {
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException;
}
