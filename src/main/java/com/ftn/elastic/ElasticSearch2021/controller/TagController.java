package com.ftn.elastic.ElasticSearch2021.controller;

import com.ftn.elastic.ElasticSearch2021.dto.TagDTO;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.TagServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/tag")
public class TagController {

    @Autowired
    TagServiceInterface tagServiceInterface;

    @GetMapping
    public ResponseEntity<List<TagDTO>> getTags(){
        return ResponseEntity.ok().body(tagServiceInterface.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TagDTO> getTag(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(tagServiceInterface.getOne(id));
    }

    @PostMapping
    public ResponseEntity<TagDTO> addTag(@RequestBody TagDTO tagDTO){
        return ResponseEntity.ok().body(tagServiceInterface.save(tagDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TagDTO> updateTag(@RequestBody TagDTO tagDTO, @PathVariable("id") Integer id){
        try{
            return ResponseEntity.ok().body(tagServiceInterface.update(id, tagDTO));
        }catch (Exception e){
            return  ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") Integer id){
        tagServiceInterface.delete(id);
        return ResponseEntity.noContent().build();
    }
}
