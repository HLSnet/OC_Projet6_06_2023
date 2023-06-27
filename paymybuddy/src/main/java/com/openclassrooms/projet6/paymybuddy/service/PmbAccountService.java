package com.openclassrooms.projet6.paymybuddy.service;



import com.openclassrooms.projet6.paymybuddy.dto.PmbAccountDto;
import com.openclassrooms.projet6.paymybuddy.model.PmbAccount;
import com.openclassrooms.projet6.paymybuddy.repository.PmbAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PmbAccountService {

    @Autowired
    private PmbAccountRepository pmbAccountRepository;

    public List<PmbAccountDto> getPmbAccounts(){
        List<PmbAccount> pmbAccounts = pmbAccountRepository.findAll();

        List<PmbAccountDto> pmbAccountDtos = new ArrayList<>();
        for (PmbAccount pmbAccount: pmbAccounts ) {
            PmbAccountDto pmbAccountDto = new PmbAccountDto();
            pmbAccountDto.setPmbAccountId(pmbAccount.getPmbAccountId());
            pmbAccountDto.setBalance(pmbAccount.getBalance());
            pmbAccountDto.setConnection(pmbAccount.getConnection());
            pmbAccountDtos.add(pmbAccountDto);
        }
        return pmbAccountDtos;
    }


    public PmbAccountDto getPmbAccountById(Integer id) {
        PmbAccount pmbAccount = pmbAccountRepository.findById(id).get();

        PmbAccountDto pmbAccountDto = new PmbAccountDto();

        pmbAccountDto.setBalance(pmbAccount.getBalance());
        pmbAccountDto.setConnection(pmbAccount.getConnection());

        return pmbAccountDto;
    }

}
