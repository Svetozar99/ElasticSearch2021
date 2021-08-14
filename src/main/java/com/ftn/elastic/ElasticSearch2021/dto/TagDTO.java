package com.ftn.elastic.ElasticSearch2021.dto;

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
}
