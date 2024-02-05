package com.example.bank.services.security;

import com.example.bank.entities.DTO.LoginResponseDTO;
import com.example.bank.entities.models.PhysicalPerson;
import com.example.bank.repository.PhysicalPersonRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements UserDetailsService {

    @Autowired
    TokenService tokenService;
    @Autowired
    private PhysicalPersonRepository physicalPersonRepository;
    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        return physicalPersonRepository.findByCpf(cpf);
    }

    public LoginResponseDTO refreshToken(HttpServletRequest request){
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        if (authHeader == null) return null;
        refreshToken = authHeader.replace("Bearer ", "");

        var cpf = tokenService.validateToken(refreshToken);
        UserDetails user = physicalPersonRepository.findByCpf(cpf);

        if (user != null) {
            var acessToken = tokenService.generateToken((PhysicalPerson) user);
            return new LoginResponseDTO(acessToken, refreshToken);
        }
        return null;
    }
}
