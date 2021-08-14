package com.ftn.elastic.ElasticSearch2021.dto;

import com.ftn.elastic.ElasticSearch2021.model.Message;
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

    public MessageDTO(Message m){
        this(m.getId(), m.getFrom(), m.getTo(), m.getCc(), m.getBcc(),
                m.getSubject(), m.getDateTime(), m.getContent(), m.getUnread(),
                new FolderDTO(m.getFolder()), new AccountDTO(m.getAccount()));
    }
}
