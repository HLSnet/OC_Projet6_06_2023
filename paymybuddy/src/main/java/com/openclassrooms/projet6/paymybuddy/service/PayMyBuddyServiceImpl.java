package com.openclassrooms.projet6.paymybuddy.service;

import com.openclassrooms.projet6.paymybuddy.dto.ConnectionDto;
import com.openclassrooms.projet6.paymybuddy.model.Connection;
import com.openclassrooms.projet6.paymybuddy.repository.ConnectionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class PayMyBuddyServiceImpl implements PayMyBuddyService{
    @Autowired
    ConnectionRepository connectionRepository;

    @Override
    public List<ConnectionDto> getBuddiesConnection(int identifiant) {
        List<ConnectionDto> connectionDtos = new ArrayList<>();

        Optional<Connection> optConnection = connectionRepository.findById(identifiant);
        if (!optConnection.isEmpty()) {
            List<Connection> buddiesConnected = optConnection.get().getBuddiesConnected();
            for (Connection connection : buddiesConnected) {
                ConnectionDto connectionDto = new ConnectionDto();
                connectionDto.setUserId(connection.getUserId());
                connectionDto.setEmail(connection.getEmail());
                connectionDto.setPassword(connection.getPassword());
                connectionDto.setPmbAccount(connection.getPmbAccount());
                connectionDto.setBuddiesConnected(connection.getBuddiesConnected());
                connectionDto.setBuddiesConnector(connection.getBuddiesConnector());
                connectionDtos.add(connectionDto);
            }
        }
        return connectionDtos;
    }
}
