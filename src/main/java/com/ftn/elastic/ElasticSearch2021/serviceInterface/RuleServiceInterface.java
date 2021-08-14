package com.ftn.elastic.ElasticSearch2021.serviceInterface;

import com.ftn.elastic.ElasticSearch2021.dto.RuleDTO;

import java.util.List;

public interface RuleServiceInterface {

    public List<RuleDTO> getAll();

    public RuleDTO getOne(Integer id);

    public RuleDTO save(RuleDTO ruleDTO);

    public RuleDTO update(Integer id, RuleDTO ruleDTO);

    public void delete(Integer id);
}
