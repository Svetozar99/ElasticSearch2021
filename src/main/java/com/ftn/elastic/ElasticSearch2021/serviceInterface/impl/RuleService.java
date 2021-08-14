package com.ftn.elastic.ElasticSearch2021.serviceInterface.impl;

import com.ftn.elastic.ElasticSearch2021.dto.FolderDTO;
import com.ftn.elastic.ElasticSearch2021.dto.RuleDTO;
import com.ftn.elastic.ElasticSearch2021.model.Folder;
import com.ftn.elastic.ElasticSearch2021.model.Rule;
import com.ftn.elastic.ElasticSearch2021.repository.FolderRepository;
import com.ftn.elastic.ElasticSearch2021.repository.RuleRepository;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.RuleServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuleService implements RuleServiceInterface {

    @Autowired
    RuleRepository ruleRepository;

    @Autowired
    FolderRepository folderRepository;

    @Override
    public List<RuleDTO> getAll() {
        List<Rule> rules = ruleRepository.findAll();

        List<RuleDTO> dtos = new ArrayList<>();
        for(Rule r: rules) {
            dtos.add(new RuleDTO(r));
        }
        return dtos;
    }

    @Override
    public RuleDTO getOne(Integer id) {
        Rule r = ruleRepository.findOneById(id);

        return new RuleDTO(r);
    }

    @Override
    public RuleDTO save(RuleDTO ruleDTO) {
        Folder f = folderRepository.findOneById(ruleDTO.getFolder().getId());

        Rule r = new Rule();
        r.setCondition(ruleDTO.getCondition());
        r.setOperation(ruleDTO.getOperation());
        r.setValue(ruleDTO.getValue());
        r.setFolder(f);

        r = ruleRepository.save(r);

        return new RuleDTO(r);
    }

    @Override
    public RuleDTO update(Integer id, RuleDTO ruleDTO) {
        Rule r = ruleRepository.findOneById(id);
        Folder f = r.getFolder();
        r.setCondition(ruleDTO.getCondition());
        r.setOperation(ruleDTO.getOperation());
        r.setValue(ruleDTO.getValue());
        r.setFolder(f);

        r = ruleRepository.save(r);

        return new RuleDTO(r);
    }

    @Override
    public void delete(Integer id) {
        ruleRepository.deleteById(id);
    }
}
