package com.ftn.elastic.ElasticSearch2021.dto;

import com.ftn.elastic.ElasticSearch2021.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
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

    private LocalDateTime syncTime;

    private UserDTO user;

    public AccountDTO(Account a){
        this(a.getId(), a.getSmtpAddress(), a.getSmtpPort(), a.getInServerType(),
                a.getInServerAddress(), a.getInServerPort(), a.getUsername(),
                a.getPassword(), a.getDisplayName(), a.getSyncTime(), new UserDTO(a.getUser()));
    }
}
