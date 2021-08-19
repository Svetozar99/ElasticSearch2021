package com.ftn.elastic.ElasticSearch2021.controller;

import com.ftn.elastic.ElasticSearch2021.dto.ContactDTO;
import com.ftn.elastic.ElasticSearch2021.dto.UserDTO;
import com.ftn.elastic.ElasticSearch2021.model.User;
import com.ftn.elastic.ElasticSearch2021.security.CustomPrincipal;
import com.ftn.elastic.ElasticSearch2021.security.annotations.CurrentPrincipal;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.ContactServiceInterface;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.UserServiceInterface;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/contact")
public class ContactController {

    @Autowired
    ContactServiceInterface contactServiceInterface;



    @Autowired
    UserService userServiceInterface;

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

    @GetMapping(value = "/my-contacts")
    public ModelAndView getMyContacts(@CurrentPrincipal CustomPrincipal principal){
        return new ModelAndView(String.format("forward:/api/contact/user/%d", 1));
    }

    @PostMapping(value = "/filter")
    public ResponseEntity<List<ContactDTO>> filterContacts(@RequestBody ContactDTO contactDTO){
        return ResponseEntity.ok().body(contactServiceInterface.filterContact(contactDTO));
    }
}
