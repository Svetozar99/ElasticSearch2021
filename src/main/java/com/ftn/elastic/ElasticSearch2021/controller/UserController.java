package com.ftn.elastic.ElasticSearch2021.controller;

import com.ftn.elastic.ElasticSearch2021.dto.UserDTO;
import com.ftn.elastic.ElasticSearch2021.model.User;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.UserServiceInterface;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.impl.MyUserDetailsService;
import com.ftn.elastic.ElasticSearch2021.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
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

    @PostMapping(value = "/register")
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

    @GetMapping(value = "by-username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable("username") String username){
        return ResponseEntity.ok().body(userServiceInterface.getByUsername(username));
    }

    @PostMapping(value = "/filter")
    public ResponseEntity<List<UserDTO>> filterUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok().body(userServiceInterface.filterUsers(userDTO));
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginDTO loginRequest){
//        UserDetails details = userServiceInterface.loadUserByUsername(loginRequest.getUsername());
//        String jwt = jwtUtil.generateToken(details);
//        return new ResponseEntity<>(jwt, HttpStatus.OK);
//    }
}
