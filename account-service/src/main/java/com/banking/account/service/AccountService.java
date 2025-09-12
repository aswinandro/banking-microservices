package com.banking.account.service;

import com.banking.account.model.Account;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account createAccount(Account account);
    Optional<Account> getAccountById(Long id);
    List<Account> getAllAccounts();
    List<Account> getAccountsByUserId(Long userId);
    Optional<Account> getAccountByNumber(String accountNumber);
    Account updateAccount(Long id, Account account);
    void deleteAccount(Long id);
}
