package com.ftn.elastic.ElasticSearch2021.repository;

import com.ftn.elastic.ElasticSearch2021.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {

}