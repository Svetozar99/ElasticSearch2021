package com.ftn.elastic.ElasticSearch2021.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", nullable = false)
    private Integer id;

    @Column(name = "_from", nullable = false)
    private String from;

    @Column(name = "_to", nullable = false)
    private String to;

    @Column(name = "_cc")
    private String cc;

    @Column(name = "_bcc")
    private String bcc;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "_subject", nullable = false)
    private String subject;

    @Lob
    @Column(name = "_content")
    private String content;

    @Column(name = "unread", nullable = false)
    private Boolean unread;

    @ManyToOne
    @JoinColumn(name = "folder_id", referencedColumnName = "folder_id")
    private Folder folder;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    private Account account;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "message")
    private Set<Attachment> attachments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "message_tag",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;
}
