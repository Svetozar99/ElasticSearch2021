package com.ftn.elastic.ElasticSearch2021.serviceInterface;


import com.ftn.elastic.ElasticSearch2021.dto.ContactDTO;

import java.util.List;

public interface ContactServiceInterface {

    public List<ContactDTO> getAll();

    public ContactDTO getOne(Integer id);

    public ContactDTO save(ContactDTO contactDTO);

    public ContactDTO update(Integer id, ContactDTO contactDTO);

    public void delete(Integer id);
}
