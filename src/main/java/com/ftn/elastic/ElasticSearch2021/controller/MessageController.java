package com.ftn.elastic.ElasticSearch2021.controller;

import com.ftn.elastic.ElasticSearch2021.dto.MessageDTO;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.MessageServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/message")
public class MessageController {

    @Autowired
    MessageServiceInterface messageServiceInterface;

    @GetMapping
    public ResponseEntity<List<MessageDTO>> getMessages(){
        return ResponseEntity.ok().body(messageServiceInterface.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MessageDTO> getMessage(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(messageServiceInterface.getOne(id));
    }

    @PostMapping
    public ResponseEntity<MessageDTO> addMessage(@RequestBody MessageDTO messageDTO){
        return ResponseEntity.ok().body(messageServiceInterface.save(messageDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<MessageDTO> updateMessage(@RequestBody MessageDTO messageDTO, @PathVariable("id") Integer id){
        try{
            return ResponseEntity.ok().body(messageServiceInterface.update(id, messageDTO));
        }catch (Exception e){
            return  ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable("id") Integer id){
        messageServiceInterface.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/by-folder/{id}")
    public ResponseEntity<List<MessageDTO>> getMessagesByFolder(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(messageServiceInterface.getByFolder(id));
    }

    @GetMapping(value = "/{id}/by-account")
    public ResponseEntity<List<MessageDTO>> getMessagesByAccount(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(messageServiceInterface.getByAccountId(id));
    }

    @GetMapping(value = "/by-folder/{folderId}/by-account/{accountId}")
    public ResponseEntity<List<MessageDTO>> getMessagesByFolderAndAccount(@PathVariable("folderId") Integer folderId, @PathVariable("accountId") Integer accountId){
        return ResponseEntity.ok().body(messageServiceInterface.getByFolderAndAccount(folderId, accountId));
    }
}
