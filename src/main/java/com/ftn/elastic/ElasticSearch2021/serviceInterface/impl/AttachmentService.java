package com.ftn.elastic.ElasticSearch2021.serviceInterface.impl;

import com.ftn.elastic.ElasticSearch2021.dto.AccountDTO;
import com.ftn.elastic.ElasticSearch2021.dto.AttachmentDTO;
import com.ftn.elastic.ElasticSearch2021.model.Account;
import com.ftn.elastic.ElasticSearch2021.model.Attachment;
import com.ftn.elastic.ElasticSearch2021.model.Message;
import com.ftn.elastic.ElasticSearch2021.model.User;
import com.ftn.elastic.ElasticSearch2021.repository.AttachmentRepository;
import com.ftn.elastic.ElasticSearch2021.repository.MessageRepository;
import com.ftn.elastic.ElasticSearch2021.repository.UserRepository;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.AttachementServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttachmentService implements AttachementServiceInterface {

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    MessageRepository messageRepository;

    @Override
    public List<AttachmentDTO> getAll() {
        List<Attachment> attachments = attachmentRepository.findAll();

        List<AttachmentDTO> dtos = new ArrayList<>();
        for(Attachment a: attachments) {
            dtos.add(new AttachmentDTO(a));
        }
        return dtos;
    }

    @Override
    public AttachmentDTO getOne(Integer id) {
        Attachment attachment = attachmentRepository.findOneById(id);

        return new AttachmentDTO(attachment);
    }

    @Override
    public AttachmentDTO save(AttachmentDTO attachmentDTO) {
        Message message = messageRepository.findOneById(attachmentDTO.getMessage().getId());
        Attachment a = new Attachment();

        a.setPath(attachmentDTO.getPath());
        a.setMimeType(attachmentDTO.getMimeType());
        a.setName(attachmentDTO.getName());
        a.setBase64(attachmentDTO.getBase64());
        a.setMessage(message);

        a = attachmentRepository.save(a);

        return new AttachmentDTO(a);
    }

    @Override
    public AttachmentDTO update(Integer id, AttachmentDTO attachmentDTO) {
        Attachment a = attachmentRepository.findOneById(id);
        Message m = a.getMessage();
        a.setPath(attachmentDTO.getPath());
        a.setMimeType(attachmentDTO.getMimeType());
        a.setName(attachmentDTO.getName());
        a.setBase64(attachmentDTO.getBase64());
        a.setMessage(m);

        a = attachmentRepository.save(a);

        return new AttachmentDTO(a);
    }

    @Override
    public void delete(Integer id) {
        attachmentRepository.deleteById(id);
    }

    @Override
    public List<AttachmentDTO> getAllByMessage(Integer id) {
        List<Attachment> attachments = attachmentRepository.findAllByMessage_id(id);

        List<AttachmentDTO> dtos = new ArrayList<>();
        for(Attachment a: attachments) {
            dtos.add(new AttachmentDTO(a));
        }
        return dtos;
    }
}
