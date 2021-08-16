package com.ftn.elastic.ElasticSearch2021.controller;

import com.ftn.elastic.ElasticSearch2021.dto.AttachmentDTO;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.AttachementServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/attachment")
public class AttachmentController {

    @Autowired
    AttachementServiceInterface attachementServiceInterface;

    @GetMapping
    public ResponseEntity<List<AttachmentDTO>> getAttachments(){
        return ResponseEntity.ok().body(attachementServiceInterface.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AttachmentDTO> getAttachment(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(attachementServiceInterface.getOne(id));
    }

    @PostMapping
    public ResponseEntity<AttachmentDTO> addAttachment(@RequestBody AttachmentDTO attachmentDTO){
        return ResponseEntity.ok().body(attachementServiceInterface.save(attachmentDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AttachmentDTO> updateAttachment(@RequestBody AttachmentDTO attachmentDTO, @PathVariable("id") Integer id){
        try{
            return ResponseEntity.ok().body(attachementServiceInterface.update(id, attachmentDTO));
        }catch (Exception e){
            return  ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable("id") Integer id){
        attachementServiceInterface.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/by-message/{id}")
    public ResponseEntity<List<AttachmentDTO>> getAttachmentsByMessage(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(attachementServiceInterface.getAllByMessage(id));
    }
}
