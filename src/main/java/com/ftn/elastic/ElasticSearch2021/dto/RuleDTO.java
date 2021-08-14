package com.ftn.elastic.ElasticSearch2021.dto;

import com.ftn.elastic.ElasticSearch2021.model.ECondition;
import com.ftn.elastic.ElasticSearch2021.model.EOperation;
import com.ftn.elastic.ElasticSearch2021.model.Rule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RuleDTO {

    private Integer id;

    private ECondition condition;

    private EOperation operation;

    private String value;

    private FolderDTO folder;

    public  RuleDTO(Rule r){
        this(r.getId(), r.getCondition(), r.getOperation(), r.getValue(), new FolderDTO(r.getFolder()));
    }
}
