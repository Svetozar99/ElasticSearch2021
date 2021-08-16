package com.ftn.elastic.ElasticSearch2021.dto;

import com.ftn.elastic.ElasticSearch2021.model.Folder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FolderDTO {

    private Integer id;

    private String name;

    private AccountDTO account;

    public FolderDTO(Folder f){
        this(f.getId(), f.getName(), new AccountDTO(f.getAccount()));
    }
}
