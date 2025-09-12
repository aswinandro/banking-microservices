package com.banking.account.config;

import com.banking.account.model.Account;
import com.banking.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void run(String... args) throws Exception {
        accountRepository.save(new Account(1L, 1L, "ACC1001", 5000.0, "SAVINGS"));
        accountRepository.save(new Account(2L, 2L, "ACC1002", 3000.0, "CHECKING"));
        accountRepository.save(new Account(3L, 3L, "ACC1003", 7000.0, "SAVINGS"));

        System.out.println("Account service initialized with " + accountRepository.count() + " accounts");
    }
}
