package com.ftn.elastic.ElasticSearch2021.serviceInterface.impl;

import com.ftn.elastic.ElasticSearch2021.dto.AccountDTO;
import com.ftn.elastic.ElasticSearch2021.model.Account;
import com.ftn.elastic.ElasticSearch2021.model.User;
import com.ftn.elastic.ElasticSearch2021.repository.AccountRepository;
import com.ftn.elastic.ElasticSearch2021.repository.UserRepository;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.AccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService implements AccountServiceInterface {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<AccountDTO> getAll() {
        List<Account> accounts = accountRepository.findAll();

        List<AccountDTO> dtos = new ArrayList<>();
        for(Account a: accounts) {
            dtos.add(new AccountDTO(a));
        }
        return dtos;
    }

    @Override
    public AccountDTO getOne(Integer id) {
        Account jedinicaMere = accountRepository.findOneById(id);

        return new AccountDTO(jedinicaMere);
    }

    @Override
    public AccountDTO save(AccountDTO accountDTO) {

        User u = userRepository.getById(accountDTO.getUser().getId());

        Account a = new Account();
        a.setSmtpAddress(accountDTO.getSmtpAddress());
        a.setSmtpPort(accountDTO.getSmtpPort());
        a.setInServerType(accountDTO.getInServerType());
        a.setInServerAddress(accountDTO.getInServerAddress());
        a.setInServerPort(accountDTO.getInServerPort());
        a.setUsername(accountDTO.getUsername());
        a.setPassword(accountDTO.getPassword());
        a.setDisplayName(accountDTO.getDisplayName());
        a.setUser(u);

        a = accountRepository.save(a);

        return new AccountDTO(a);
    }

    @Override
    public AccountDTO update(Integer id, AccountDTO accountDTO) {

        Account a = accountRepository.findOneById(id);
        User u = a.getUser();
        a.setSmtpAddress(accountDTO.getSmtpAddress());
        a.setSmtpPort(accountDTO.getSmtpPort());
        a.setInServerType(accountDTO.getInServerType());
        a.setInServerAddress(accountDTO.getInServerAddress());
        a.setInServerPort(accountDTO.getInServerPort());
        a.setUsername(accountDTO.getUsername());
        a.setPassword(accountDTO.getPassword());
        a.setDisplayName(accountDTO.getDisplayName());
        a.setUser(u);

        a = accountRepository.save(a);

        return new AccountDTO(a);
    }

    @Override
    public void delete(Integer id) {
        accountRepository.deleteById(id);
    }
}
