package com.ftn.elastic.ElasticSearch2021.serviceInterface;


import com.ftn.elastic.ElasticSearch2021.dto.AttachmentDTO;

import java.util.List;

public interface AttachementServiceInterface {

    public List<AttachmentDTO> getAll();

    public AttachmentDTO getOne(Integer id);

    public AttachmentDTO save(AttachmentDTO attachmentDTO);

    public AttachmentDTO update(Integer id, AttachmentDTO attachmentDTO);

    public void delete(Integer id);
}
