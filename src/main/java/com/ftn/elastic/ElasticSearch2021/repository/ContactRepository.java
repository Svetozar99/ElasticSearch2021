package com.ftn.elastic.ElasticSearch2021.repository;

import com.ftn.elastic.ElasticSearch2021.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

}