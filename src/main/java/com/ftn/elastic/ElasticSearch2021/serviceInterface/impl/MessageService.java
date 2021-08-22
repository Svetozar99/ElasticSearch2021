package com.ftn.elastic.ElasticSearch2021.serviceInterface.impl;

import com.ftn.elastic.ElasticSearch2021.dto.AttachmentDTO;
import com.ftn.elastic.ElasticSearch2021.dto.MessageDTO;
import com.ftn.elastic.ElasticSearch2021.lucene.indexing.Indexer;
import com.ftn.elastic.ElasticSearch2021.model.Account;
import com.ftn.elastic.ElasticSearch2021.model.Folder;
import com.ftn.elastic.ElasticSearch2021.model.Message;
import com.ftn.elastic.ElasticSearch2021.repository.AccountRepository;
import com.ftn.elastic.ElasticSearch2021.repository.FolderRepository;
import com.ftn.elastic.ElasticSearch2021.repository.MessageRepository;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.AccountServiceInterface;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.MessageServiceInterface;
import com.ftn.elastic.ElasticSearch2021.utility.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService implements MessageServiceInterface {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountServiceInterface accountServiceInterface;

    @Autowired
    Indexer indexer;

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    MailUtil mailUtil;

    @Override
    public List<MessageDTO> getAll() {
        List<Message> messages = messageRepository.findAll();

        List<MessageDTO> dtos = new ArrayList<>();
        for(Message m: messages) {
            dtos.add(new MessageDTO(m));
        }
        return dtos;
    }

    @Override
    public MessageDTO getOne(Integer id) {
        Message m = messageRepository.findOneById(id);

        return new MessageDTO(m);
    }

    @Override
    public MessageDTO save(MessageDTO messageDTO) {

        Folder f = folderRepository.findOneById(messageDTO.getFolder().getId());
        Account a = accountRepository.findOneById(messageDTO.getAccount().getId());
        System.out.println(a.getUsername() + " username");
        Message m = new Message();
        m.setFrom(messageDTO.getFrom());
        m.setTo(messageDTO.getTo());
        m.setCc(messageDTO.getCc());
        m.setBcc(messageDTO.getBcc());
        m.setSubject(messageDTO.getSubject());
        m.setDateTime(messageDTO.getDateTime());
        m.setContent(messageDTO.getContent());
        m.setUnread(messageDTO.getUnread());
        m.setFolder(f);
        m.setAccount(a);

//        boolean sentSuccessful =
        mailUtil.sendMessage(m);
//        if(sentSuccessful)
//            m = messageRepository.save(m);

        return new MessageDTO(m);
    }

    @Override
    public MessageDTO update(Integer id, MessageDTO messageDTO) {
        Message m = new Message();
        Folder f = m.getFolder();
        Account a = m.getAccount();
        m.setFrom(messageDTO.getFrom());
        m.setTo(messageDTO.getTo());
        m.setCc(messageDTO.getCc());
        m.setBcc(messageDTO.getBcc());
        m.setSubject(messageDTO.getSubject());
        m.setDateTime(messageDTO.getDateTime());
        m.setContent(messageDTO.getContent());
        m.setUnread(messageDTO.getUnread());
        m.setFolder(f);
        m.setAccount(a);

        m = messageRepository.save(m);

        return new MessageDTO(m);
    }

    @Override
    public void delete(Integer id) {
        messageRepository.deleteById(id);
    }

    @Override
    public List<MessageDTO> getByFolder(Integer id) {
        if(folderRepository.findOneById(id) == null)
            throw new EntityNotFoundException();

        List<Message> messages = messageRepository.findAllByFolder_id(id);

        List<MessageDTO> dtos = new ArrayList<>();
        for(Message m: messages) {
            dtos.add(new MessageDTO(m));
        }
        return dtos;
    }

    @Override
    public List<MessageDTO> getByAccountId(Integer id) {
        if(accountRepository.findOneById(id) == null)
            throw new EntityNotFoundException();

        List<Message> messages = messageRepository.findAllByAccount_id(id);

        List<MessageDTO> dtos = new ArrayList<>();
        for(Message m: messages) {
            dtos.add(new MessageDTO(m));
        }
        return dtos;
    }

    @Override
    public List<MessageDTO> getByAccount(Integer id) {
        Account account = accountRepository.getById(id);
        System.out.println(account.getId() + "account");
        if(account == null) throw new EntityNotFoundException();
//        Account account = accountConverter.convertToJPA(accountDTO);
        List<MessageDTO> allMessages = new ArrayList<>();

        List<Message> loadMessages = mailUtil.pullMessages(account);
        loadMessages = messageRepository.saveAll(loadMessages);
        if(loadMessages.isEmpty()){
            //return this.getByAccountId(accountId);
            allMessages.addAll(this.getByFolderNameAndAccount("INBOX", id)); }
        else {
            List<MessageDTO> savedMessages = this.getByFolderNameAndAccount("INBOX", id);

            List<MessageDTO> pulledMessages = new ArrayList<>();
            for(Message m: loadMessages) {
                pulledMessages.add(new MessageDTO(m));
            }

            allMessages.addAll(savedMessages);
            allMessages.addAll(pulledMessages);
        }

        allMessages.forEach(messageDTO -> {
            try {
                if(!indexer.existMessage(messageDTO.getId())){
                    indexer.addMessage(messageDTO);
                    List<AttachmentDTO> attachmentDTOS = attachmentService.getAllByMessage(messageDTO.getId());
                    if(!attachmentDTOS.isEmpty() && attachmentDTOS != null){
                        attachmentDTOS.forEach(attachmentDTO -> {
                            try {
                                if(!indexer.existAttachment(attachmentDTO.getId())){
                                    indexer.addAttachment(attachmentDTO);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return allMessages;
    }

    @Override
    public List<MessageDTO> getByFolderAndAccount(Integer folderId, Integer accountId) {
        if(folderRepository.findOneById(folderId) == null || accountRepository.findOneById(accountId) == null)
            throw new EntityNotFoundException();

        List<Message> messages = messageRepository.findAllByFolder_idAndAccount_id(folderId, accountId);

        List<MessageDTO> dtos = new ArrayList<>();
        for(Message m: messages) {
            dtos.add(new MessageDTO(m));
        }
        return dtos;
    }

    @Override
    public List<MessageDTO> getByFolderNameAndAccount(String folderId, Integer accountId) {
        List<Message> messages = messageRepository.findAllByFolder_nameAndAccount_id(folderId, accountId);

        List<MessageDTO> dtos = new ArrayList<>();
        for(Message m: messages) {
            dtos.add(new MessageDTO(m));
        }
        return dtos;
    }
}
