package com.ftn.elastic.ElasticSearch2021.repository;

import com.ftn.elastic.ElasticSearch2021.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    Message findOneById(Integer id);

    List<Message> findAllByFolder_id(Integer id);

    List<Message> findAllByAccount_id(Integer id);

    List<Message> findAllByFolder_idAndAccount_id(Integer folderId, Integer messageId);

    List<Message> findAllByFolder_nameAndAccount_id(String folderName, Integer messageId);
}