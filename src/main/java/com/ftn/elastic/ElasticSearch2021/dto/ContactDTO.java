package com.ftn.elastic.ElasticSearch2021.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContactDTO {

    private Integer id;

    private String firstName;

    private String lastName;

    private String displayName;

    private String email;

    private String note;

    private UserDTO user;
}
