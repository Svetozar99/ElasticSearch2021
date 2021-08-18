package com.ftn.elastic.ElasticSearch2021.controller;

import com.ftn.elastic.ElasticSearch2021.dto.AccountDTO;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.AccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/account")
public class AccountController {

    @Autowired
    AccountServiceInterface accountServiceInterface;

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAccounts(){
        return ResponseEntity.ok().body(accountServiceInterface.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(accountServiceInterface.getOne(id));
    }

    @PostMapping
    public ResponseEntity<AccountDTO> addAccount(@RequestBody AccountDTO accountDTO){
        return ResponseEntity.ok().body(accountServiceInterface.save(accountDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO accountDTO, @PathVariable("id") Integer id){
        try{
            return ResponseEntity.ok().body(accountServiceInterface.update(id, accountDTO));
        }catch (Exception e){
            return  ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") Integer id){
        accountServiceInterface.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<AccountDTO>> getAccountsBy(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(accountServiceInterface.getByUser(id));
    }

    @GetMapping(value = "/by-username/{username}/get")
    public ResponseEntity<AccountDTO> getAccountByUsername(@PathVariable("username") String username){
        return ResponseEntity.ok().body(accountServiceInterface.getByUsername(username));
    }
}
