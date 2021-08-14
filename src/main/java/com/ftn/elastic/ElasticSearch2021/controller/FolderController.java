package com.ftn.elastic.ElasticSearch2021.controller;

import com.ftn.elastic.ElasticSearch2021.dto.FolderDTO;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.FolderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/folder")
public class FolderController {

    @Autowired
    FolderServiceInterface folderServiceInterface;

    @GetMapping
    public ResponseEntity<List<FolderDTO>> getFolders(){
        return ResponseEntity.ok().body(folderServiceInterface.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FolderDTO> getFolder(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(folderServiceInterface.getOne(id));
    }

    @PostMapping
    public ResponseEntity<FolderDTO> addFolder(@RequestBody FolderDTO folderDTO){
        return ResponseEntity.ok().body(folderServiceInterface.save(folderDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<FolderDTO> updateFolder(@RequestBody FolderDTO folderDTO, @PathVariable("id") Integer id){
        try{
            return ResponseEntity.ok().body(folderServiceInterface.update(id, folderDTO));
        }catch (Exception e){
            return  ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteFolder(@PathVariable("id") Integer id){
        folderServiceInterface.delete(id);
        return ResponseEntity.noContent().build();
    }
}
