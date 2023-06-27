package com.openclassrooms.projet6.paymybuddy.service;


import com.openclassrooms.projet6.paymybuddy.dto.ConnectionDto;

import java.util.List;

public interface PayMyBuddyService {
    List<ConnectionDto> getBuddiesConnection(int identifiant);

}
