package com.ftn.elastic.ElasticSearch2021.dto;

import com.ftn.elastic.ElasticSearch2021.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TagDTO {
    private Integer id;

    private String name;

    private UserDTO user;

    public TagDTO(Tag tag){
        this(tag.getId(), tag.getName(), new UserDTO(tag.getUser()));
    }
}
