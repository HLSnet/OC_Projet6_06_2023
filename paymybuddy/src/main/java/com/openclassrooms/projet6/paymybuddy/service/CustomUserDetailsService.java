package com.openclassrooms.projet6.paymybuddy.service;


import com.openclassrooms.projet6.paymybuddy.model.Connection;
import com.openclassrooms.projet6.paymybuddy.repository.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ConnectionRepository connectionRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Connection connection = connectionRepository.findByEmail(email).get();
        if (connection != null) {
            return new org.springframework.security.core.userdetails.User(connection.getEmail()
                    , connection.getPassword(), new ArrayList<>());
        }
        else { throw new UsernameNotFoundException("Invalid email");  }
    }
}