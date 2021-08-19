package com.ftn.elastic.ElasticSearch2021.controller;

import com.ftn.elastic.ElasticSearch2021.dto.LoginDTO;
import com.ftn.elastic.ElasticSearch2021.dto.UserDTO;
import com.ftn.elastic.ElasticSearch2021.model.User;
import com.ftn.elastic.ElasticSearch2021.security.util.JwtUtil;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.UserServiceInterface;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.impl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "login")
public class LoginController {

//    @Autowired
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    MyUserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserServiceInterface userServiceInterface;

//    @PostMapping(consumes = "application/json")
//    public ResponseEntity<?> login(@RequestBody Map<String, String> userCred) throws Exception {
//        String username = userCred.get("username");
//        String password = userCred.get("password");
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password)
//            );
//        }
//        catch (BadCredentialsException e) {
//            return ResponseEntity.notFound().build();
//        }
//
//        final UserDetails userDetails =  userDetailsService.loadUserByUsername(username);
//
//        final String jwt = jwtUtil.generateToken(userDetails);
//        Map<String, Object> response = new HashMap<>();
//        User u = userServiceInterface.getUserByUsername(username).get();
//        u.setPassword(null);
//        response.put("user", new UserDTO(u));
//        response.put("token", jwt);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginRequest){
        UserDetails details = userServiceInterface.loadUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        String jwt = jwtUtil.generateToken(details);
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }
}
