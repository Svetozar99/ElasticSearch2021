package com.ftn.elastic.ElasticSearch2021.dto;

import com.ftn.elastic.ElasticSearch2021.model.Attachment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AttachmentDTO {

    private Integer id;

    private String path;

    private String mimeType;

    private String name;

    private String base64;

    private MessageDTO message;


    public AttachmentDTO(Attachment a){
        this(a.getId(), a.getPath(), a.getMimeType(), a.getName(), a.getBase64(), new MessageDTO(a.getMessage()));
    }
}
