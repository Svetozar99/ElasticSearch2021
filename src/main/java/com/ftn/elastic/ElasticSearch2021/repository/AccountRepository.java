package com.ftn.elastic.ElasticSearch2021.repository;

import com.ftn.elastic.ElasticSearch2021.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}