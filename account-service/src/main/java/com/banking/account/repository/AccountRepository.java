package com.banking.account.repository;

import com.banking.account.model.Account;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class AccountRepository {
    private final Map<Long, Account> accounts = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Account save(Account account) {
        if (account.getId() == null) {
            account.setId(idGenerator.getAndIncrement());
        }
        accounts.put(account.getId(), account);
        return account;
    }

    public Optional<Account> findById(Long id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public List<Account> findAll() {
        return new ArrayList<>(accounts.values());
    }

    public List<Account> findByUserId(Long userId) {
        List<Account> userAccounts = new ArrayList<>();
        for (Account account : accounts.values()) {
            if (account.getUserId().equals(userId)) {
                userAccounts.add(account);
            }
        }
        return userAccounts;
    }

    public Optional<Account> findByAccountNumber(String accountNumber) {
        return accounts.values().stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst();
    }

    public void deleteById(Long id) {
        accounts.remove(id);
    }

    public boolean existsById(Long id) {
        return accounts.containsKey(id);
    }

    public long count() {
        return accounts.size();
    }
}
