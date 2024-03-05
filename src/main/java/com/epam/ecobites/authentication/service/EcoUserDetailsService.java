package com.epam.ecobites.authentication.service;

import com.epam.ecobites.authentication.test.TestEcoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EcoUserDetailsService implements UserDetailsService {
    private final TestEcoUserRepository repository;

    @Autowired
    public EcoUserDetailsService(TestEcoUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = repository.getUser(username);
        if (userDetails != null) {
            return userDetails;
        }
        throw new UsernameNotFoundException(username);
    }
}
