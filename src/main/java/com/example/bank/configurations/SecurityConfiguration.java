package com.example.bank.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "v1/physical-person/create").permitAll()

                        .requestMatchers(HttpMethod.GET, "v1/account/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "v1/physical-person").authenticated()
                        .requestMatchers(HttpMethod.GET, "v1/physical-person/{id}").authenticated()
                        .requestMatchers(HttpMethod.PUT, "v1/physical-person/edit/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "v1/physical-person/delete/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "v1/physical-person/deposit/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "v1/physical-person/withdraw/{id}").authenticated()
                        .anyRequest().permitAll()

                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
