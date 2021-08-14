package com.ftn.elastic.ElasticSearch2021.controller;

import com.ftn.elastic.ElasticSearch2021.dto.RuleDTO;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.RuleServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/rule")
public class RuleController {

    @Autowired
    RuleServiceInterface ruleServiceInterface;

    @GetMapping
    public ResponseEntity<List<RuleDTO>> getRules(){
        return ResponseEntity.ok().body(ruleServiceInterface.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RuleDTO> getRule(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(ruleServiceInterface.getOne(id));
    }

    @PostMapping
    public ResponseEntity<RuleDTO> addRule(@RequestBody RuleDTO ruleDTO){
        return ResponseEntity.ok().body(ruleServiceInterface.save(ruleDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RuleDTO> updateRule(@RequestBody RuleDTO ruleDTO, @PathVariable("id") Integer id){
        try{
            return ResponseEntity.ok().body(ruleServiceInterface.update(id, ruleDTO));
        }catch (Exception e){
            return  ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable("id") Integer id){
        ruleServiceInterface.delete(id);
        return ResponseEntity.noContent().build();
    }
}
