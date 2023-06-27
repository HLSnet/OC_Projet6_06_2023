package com.openclassrooms.projet6.paymybuddy.service;

import com.openclassrooms.projet6.paymybuddy.dto.ConnectionDto;
import com.openclassrooms.projet6.paymybuddy.model.Connection;
import com.openclassrooms.projet6.paymybuddy.repository.ConnectionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class ConnectionService {
    @Autowired
    private ConnectionRepository connectionRepository;

    public List<ConnectionDto> getConnections(){
        List<Connection> connections = connectionRepository.findAll();

        List<ConnectionDto> connectionDtos = new ArrayList<>();
        for (Connection connection: connections ) {
            ConnectionDto connectionDto = new ConnectionDto();
            connectionDto.setUserId(connection.getUserId());
            connectionDto.setEmail(connection.getEmail());
            connectionDto.setPassword(connection.getPassword());
            connectionDto.setPmbAccount(connection.getPmbAccount());
            connectionDtos.add(connectionDto);
        }
        return connectionDtos;
    }

    public ConnectionDto getConnectionById(Integer id){
        Connection connection = connectionRepository.findById(id).get();

        ConnectionDto connectionDto = new ConnectionDto();

        connectionDto.setUserId(connection.getUserId());
        connectionDto.setEmail(connection.getEmail());
        connectionDto.setPassword(connection.getPassword());
        connectionDto.setPmbAccount(connection.getPmbAccount());

        return connectionDto;
    }


}
