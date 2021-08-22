package com.ftn.elastic.ElasticSearch2021.controller;

import com.ftn.elastic.ElasticSearch2021.lucene.indexing.Indexer;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.impl.AttachmentService;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.impl.ContactService;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.impl.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class IndexController {

    @Autowired
    private Indexer indexer;

    @Autowired
    private MessageService messageService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private ContactService contactService;

    @GetMapping("/reindex-messages/account/{id}")
    public ResponseEntity<String> indexMessages(@PathVariable("id") int id) throws IOException {
        long start = new Date().getTime();
        int numIndexed = indexer.getInstance().indexMessages(messageService.getByAccount(id));
        long end = new Date().getTime();
        String text = "Indexing " + numIndexed + " object took "
                + (end - start) + " milliseconds";
        return new ResponseEntity<>(text, HttpStatus.OK);
    }

    @GetMapping("/reindex-attachments/message/{id}")
    public ResponseEntity<String> indexAttachment(@PathVariable("id") Integer id) throws IOException {
        long start = new Date().getTime();
        int numIndexed = Indexer.getInstance().indexAttachments(attachmentService.getAllByMessage(id));
        long end = new Date().getTime();
        String text = "Indexing " + numIndexed + " object took "
                + (end - start) + " milliseconds";
        return new ResponseEntity<>(text, HttpStatus.OK);
    }

    @GetMapping("/reindex-contact")
    public ResponseEntity<String> indexContacts() throws IOException {
            long start = new Date().getTime();
            int numIndexed = Indexer.getInstance().indexContacts(contactService.getAllByUser(1));
            long end = new Date().getTime();
            String text = "Indexing " + numIndexed + " object took "
                    + (end - start) + " milliseconds";
            return new ResponseEntity<>(text, HttpStatus.OK);
        }
}