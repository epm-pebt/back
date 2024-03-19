package com.epam.ecobites.authentication.service;

import com.epam.ecobites.data.EcoUserRepository;
import com.epam.ecobites.domain.EcoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EcoUserDetailsService implements UserDetailsService {
    private final EcoUserRepository repository;

    @Autowired
    public EcoUserDetailsService(EcoUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EcoUser user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new EcoUserDetails(user.getUsername(), user.getPassword());
    }
}
