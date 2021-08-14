package com.ftn.elastic.ElasticSearch2021.serviceInterface;

import com.ftn.elastic.ElasticSearch2021.dto.MessageDTO;

import java.util.List;

public interface MessageServiceInterface {
    public List<MessageDTO> getAll();

    public MessageDTO getOne(Integer id);

    public MessageDTO save(MessageDTO messageDTO);

    public MessageDTO update(Integer id, MessageDTO messageDTO);

    public void delete(Integer id);
}
