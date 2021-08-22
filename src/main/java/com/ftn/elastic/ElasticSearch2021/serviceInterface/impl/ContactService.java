package com.ftn.elastic.ElasticSearch2021.serviceInterface.impl;

import com.ftn.elastic.ElasticSearch2021.dto.ContactDTO;
import com.ftn.elastic.ElasticSearch2021.dto.UserDTO;
import com.ftn.elastic.ElasticSearch2021.lucene.indexing.Indexer;
import com.ftn.elastic.ElasticSearch2021.model.Contact;
import com.ftn.elastic.ElasticSearch2021.model.User;
import com.ftn.elastic.ElasticSearch2021.repository.ContactRepository;
import com.ftn.elastic.ElasticSearch2021.repository.UserRepository;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.ContactServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService implements ContactServiceInterface {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Indexer indexer;

    @Override
    public List<ContactDTO> getAll() {
        List<Contact> contacts = contactRepository.findAll();

        List<ContactDTO> dtos = new ArrayList<>();
        for(Contact c: contacts) {
            dtos.add(new ContactDTO(c));
        }
        return dtos;
    }

    @Override
    public ContactDTO getOne(Integer id) {
        Contact contact = contactRepository.findOneById(id);

        return new ContactDTO(contact);
    }

    @Override
    public ContactDTO save(ContactDTO contactDTO) {

        User u = userRepository.findOneById(contactDTO.getUser().getId());

        Contact c = new Contact();
        c.setFirstName(contactDTO.getFirstName());
        c.setLastName(contactDTO.getLastName());
        c.setDisplayName(contactDTO.getDisplayName());
        c.setEmail(contactDTO.getEmail());
        c.setNote(contactDTO.getNote());
        c.setUser(u);

        c = contactRepository.save(c);

        return new ContactDTO(c);
    }

    @Override
    public ContactDTO update(Integer id, ContactDTO contactDTO) {
        Contact c = contactRepository.findOneById(id);

        User u = c.getUser();
        c.setFirstName(contactDTO.getFirstName());
        c.setLastName(contactDTO.getLastName());
        c.setDisplayName(contactDTO.getDisplayName());
        c.setEmail(contactDTO.getEmail());
        c.setNote(contactDTO.getNote());
        c.setUser(u);

        c = contactRepository.save(c);

        return new ContactDTO(c);
    }

    @Override
    public void delete(Integer id) {
        contactRepository.deleteById(id);
    }

    @Override
    public List<ContactDTO> getAllByUser(Integer id) {
        List<Contact> contacts = contactRepository.findAllByUser_id(id);

        List<ContactDTO> dtos = new ArrayList<>();
        for(Contact c: contacts) {
            dtos.add(new ContactDTO(c));
        }

        dtos.forEach(contactDTOo -> {
            try{
                if(!indexer.existContact(contactDTOo.getId())){
                    indexer.addContact(contactDTOo);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        });
        return dtos;
    }

    @Override
    public List<ContactDTO> filterContact(ContactDTO contactDTO) {
        List<Contact> contacts = contactRepository.filterContacts(contactDTO.getFirstName(), contactDTO.getLastName(), contactDTO.getEmail(), contactDTO.getNote());

        List<ContactDTO> dtos = new ArrayList<>();

        dtos.forEach(contactDTOo -> {
            try{
                if(!indexer.existContact(contactDTOo.getId())){
                    indexer.addContact(contactDTOo);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        });
//        for(Contact c: contacts) {
//            dtos.add(new ContactDTO(c));
//        }
        return dtos;
    }
}
