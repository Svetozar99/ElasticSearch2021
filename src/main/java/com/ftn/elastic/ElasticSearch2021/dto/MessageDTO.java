package com.ftn.elastic.ElasticSearch2021.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessageDTO {

    private Integer id;

    private String from;

    private String to;

    private String cc;

    private String bcc;

    private String subject;

    private LocalDateTime dateTime;

    private String content;

    private Boolean unread;

    private FolderDTO folder;

    private AccountDTO account;

}
