package com.ftn.elastic.ElasticSearch2021.controller;

import com.ftn.elastic.ElasticSearch2021.dto.ContactDTO;
import com.ftn.elastic.ElasticSearch2021.dto.UserDTO;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.ContactServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/contact")
public class ContactController {

    @Autowired
    ContactServiceInterface contactServiceInterface;

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getContacts(){
        return ResponseEntity.ok().body(contactServiceInterface.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ContactDTO> getContact(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(contactServiceInterface.getOne(id));
    }

    @PostMapping
    public ResponseEntity<ContactDTO> addContact(@RequestBody ContactDTO contactDTO){
        return ResponseEntity.ok().body(contactServiceInterface.save(contactDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ContactDTO> updateContact(@RequestBody ContactDTO contactDTO, @PathVariable("id") Integer id){
        try{
            return ResponseEntity.ok().body(contactServiceInterface.update(id, contactDTO));
        }catch (Exception e){
            return  ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable("id") Integer id){
        contactServiceInterface.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<ContactDTO>> getContactsByUser(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(contactServiceInterface.getAllByUser(id));
    }

    @PostMapping(value = "/filter")
    public ResponseEntity<List<ContactDTO>> filterContacts(@RequestBody ContactDTO contactDTO){
        return ResponseEntity.ok().body(contactServiceInterface.filterContact(contactDTO));
    }
}
