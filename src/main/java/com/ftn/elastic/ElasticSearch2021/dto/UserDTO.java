package com.ftn.elastic.ElasticSearch2021.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Integer id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;
}
