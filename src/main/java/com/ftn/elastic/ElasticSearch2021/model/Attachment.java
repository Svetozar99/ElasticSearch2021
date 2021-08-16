package com.ftn.elastic.ElasticSearch2021.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "attachments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Integer id;

    @Column(name = "_path", nullable = false)
    private String path;

    @Column(name = "mime_type", nullable = false, length = 20, columnDefinition = "LONGBLOB")
    private String mimeType;

    @Column(name = "attach_name", nullable = false, length = 100)
    private String name;

    @Column(name = "base64", columnDefinition = "LONGTEXT")
    private String base64;



    @ManyToOne
    @JoinColumn(name = "message_id", referencedColumnName = "message_id", nullable = false)
    private Message message;
}
