package com.example.service;

import com.example.entity.Account;
import com.example.exception.*;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account verifyLogin(Account account) {
        return accountRepository.findByUsername(account.getUsername())
                .filter(a -> a.getPassword().equals(account.getPassword()))
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials."));
    }

    public boolean usernameExists(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }
}
