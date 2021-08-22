package com.ftn.elastic.ElasticSearch2021.serviceInterface;

import com.ftn.elastic.ElasticSearch2021.dto.MessageDTO;

import java.util.List;

public interface MessageServiceInterface {
    public List<MessageDTO> getAll();

    public MessageDTO getOne(Integer id);

    public MessageDTO save(MessageDTO messageDTO);

    public MessageDTO update(Integer id, MessageDTO messageDTO);

    public void delete(Integer id);

    public List<MessageDTO> getByFolder(Integer id);

    public List<MessageDTO> getByAccountId(Integer id);

    public List<MessageDTO> getByAccount(Integer id);

    public List<MessageDTO> getByFolderAndAccount(Integer folderId, Integer accountId);

    public List<MessageDTO> getByFolderNameAndAccount(String folderId, Integer accountId);

}
