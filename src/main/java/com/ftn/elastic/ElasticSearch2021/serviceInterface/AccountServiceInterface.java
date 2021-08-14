package com.ftn.elastic.ElasticSearch2021.serviceInterface;

import com.ftn.elastic.ElasticSearch2021.dto.AccountDTO;
import com.ftn.elastic.ElasticSearch2021.model.Account;

import java.util.List;

public interface AccountServiceInterface {

    public List<AccountDTO> getAll();

    public AccountDTO getOne(Integer id);

    public AccountDTO save(AccountDTO accountDTO);

    public AccountDTO update(Integer id, AccountDTO accountDTO);

    public void delete(Integer id);
}
