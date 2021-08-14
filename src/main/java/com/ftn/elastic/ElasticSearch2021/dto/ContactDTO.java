package com.ftn.elastic.ElasticSearch2021.dto;

import com.ftn.elastic.ElasticSearch2021.model.Contact;
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

    public ContactDTO(Contact c){
        this(c.getId(), c.getFirstName(), c.getLastName(),
                c.getDisplayName(), c.getEmail(), c.getNote(), new UserDTO(c.getUser()));
    }
}
