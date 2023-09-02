package com.openclassrooms.projet6.paymybuddy.service;


import com.openclassrooms.projet6.paymybuddy.model.Connection;
import com.openclassrooms.projet6.paymybuddy.repository.ConnectionRepository;
import com.openclassrooms.projet6.paymybuddy.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ConnectionRepository connectionRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Connection> optConnection = connectionRepository.findByEmail(email);

        if (optConnection == null) throw new UsernameNotFoundException("Invalid email");

        // connection non null
        Connection connection = optConnection.get();

        CustomUserDetails customUserDetails = new CustomUserDetails(connection.getConnectionId(),
                                                                    connection.getEmail(),
                                                                    connection.getPassword(),
                                                                    new ArrayList<>());

        return customUserDetails;
    }
}