package com.ftn.elastic.ElasticSearch2021.repository;

import com.ftn.elastic.ElasticSearch2021.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}