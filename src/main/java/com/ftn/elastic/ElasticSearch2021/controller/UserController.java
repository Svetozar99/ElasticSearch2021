package com.ftn.elastic.ElasticSearch2021.controller;

import com.ftn.elastic.ElasticSearch2021.dto.AccountDTO;
import com.ftn.elastic.ElasticSearch2021.dto.UserDTO;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/user")
public class UserController {

    @Autowired
    UserServiceInterface userServiceInterface;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(){
        return ResponseEntity.ok().body(userServiceInterface.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(userServiceInterface.getOne(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok().body(userServiceInterface.save(userDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable("id") Integer id){
        try{
            return ResponseEntity.ok().body(userServiceInterface.update(id, userDTO));
        }catch (Exception e){
            return  ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id){
        userServiceInterface.delete(id);
        return ResponseEntity.noContent().build();
    }
}
