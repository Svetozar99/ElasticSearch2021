package com.ftn.elastic.ElasticSearch2021.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AccountDTO {

    private Integer id;

    private String smtpAddress;

    private int smtpPort;

    private int inServerType;

    private String inServerAddress;

    private int inServerPort;

    private String username;

    private String password;

    private String displayName;

    private UserDTO user;
}
