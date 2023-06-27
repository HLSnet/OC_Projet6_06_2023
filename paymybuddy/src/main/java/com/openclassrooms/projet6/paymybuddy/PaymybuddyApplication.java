package com.openclassrooms.projet6.paymybuddy;

import com.openclassrooms.projet6.paymybuddy.dto.ConnectionDto;
import com.openclassrooms.projet6.paymybuddy.dto.PmbAccountDto;
import com.openclassrooms.projet6.paymybuddy.service.ConnectionService;
import com.openclassrooms.projet6.paymybuddy.service.PayMyBuddyService;
import com.openclassrooms.projet6.paymybuddy.service.PmbAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymybuddyApplication implements CommandLineRunner {

	@Autowired
	private ConnectionService connectionService;

	@Autowired
	private PmbAccountService pmbAccountService;

	@Autowired
	private PayMyBuddyService payMyBuddyServiceImpl;

	public static void main(String[] args) {
		SpringApplication.run(PaymybuddyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		for (ConnectionDto connectionDto: connectionService.getConnections())
//		{
//			System.out.println(connectionDto.getEmail() + "  " + connectionDto.getPassword());
//		}
//
//		ConnectionDto connectionDto = connectionService.getConnectionById(2);
//		System.out.println(connectionDto.getEmail() + "  " + connectionDto.getPassword() );
//		System.out.println("Balance : " + connectionDto.getPmbAccount().getBalance());
//
//
//		for (PmbAccountDto pmbAccountDto: pmbAccountService.getPmbAccounts())
//		{
//			System.out.println(pmbAccountDto.getBalance());
//		}
//
//		PmbAccountDto pmbAccountDto = pmbAccountService.getPmbAccountById(2);
//		System.out.println("Balance : " + pmbAccountDto.getBalance());
//		System.out.println("Email :" + pmbAccountDto.getConnection().getEmail());

//
//		for (ConnectionDto buddyConnection: payMyBuddyServiceImpl.getBuddiesConnection(2))
//		{
//			System.out.println(buddyConnection.getEmail() + "  " + buddyConnection.getPassword());
//		}
//

	}
}
