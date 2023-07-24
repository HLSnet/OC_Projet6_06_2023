package com.openclassrooms.projet6.paymybuddy;

import com.openclassrooms.projet6.paymybuddy.model.Connection;
import com.openclassrooms.projet6.paymybuddy.repository.ConnectionRepository;
import com.openclassrooms.projet6.paymybuddy.repository.PmbAccountRepository;
import com.openclassrooms.projet6.paymybuddy.service.PayMyBuddyService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;

@SpringBootApplication

public class PaymybuddyApplication implements CommandLineRunner {
//
//	@Autowired
//	private ConnectionService connectionService;
//
//	@Autowired
//	private PmbAccountService pmbAccountService;

	@Autowired
	private PayMyBuddyService payMyBuddyServiceImpl;

	@Autowired
	private PmbAccountRepository pmbAccountRepository;

	@Autowired
	private ConnectionRepository connectionRepository;


	public static void main(String[] args) {
		SpringApplication.run(PaymybuddyApplication.class, args);
	}

	@Override
	@Transactional
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

//
//		Connection newConnection = new Connection();
//        String emailNewConnection= "user10@gmail.com";
//        String passwordNewConnection= "pwd10";
//        newConnection.setEmail(emailNewConnection);
//        newConnection.setPassword(passwordNewConnection);
//
//		PmbAccount newPmbAccount = new PmbAccount();
//
//		Connection connection = connectionRepository.save(newConnection);
//		newPmbAccount.setConnection(connection);
//		connection.setPmbAccount(newPmbAccount);


	}
}
