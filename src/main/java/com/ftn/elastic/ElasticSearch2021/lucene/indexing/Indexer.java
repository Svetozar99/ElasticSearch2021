package com.ftn.elastic.ElasticSearch2021.lucene.indexing;

import com.ftn.elastic.ElasticSearch2021.dto.AttachmentDTO;
import com.ftn.elastic.ElasticSearch2021.dto.ContactDTO;
import com.ftn.elastic.ElasticSearch2021.dto.MessageDTO;
import com.ftn.elastic.ElasticSearch2021.lucene.indexing.handlers.PDFHandler;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class Indexer {

    private JestClient jestClient;

    private static final Logger log = LoggerFactory.getLogger(Indexer.class);

    private static Indexer indexer = new Indexer();

    public static Indexer getInstance(){ return indexer; }

    private Indexer() {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(
                new HttpClientConfig.Builder("http://localhost:9200")
                        .multiThreaded(true)
                        .defaultMaxTotalConnectionPerRoute(2)
                        .maxTotalConnection(10)
                        .build());
        jestClient = factory.getObject();
    }

    public int indexMessages(List<MessageDTO> messages) throws IOException {
        JestResult result = null;
        int retVal = 0;
        for(MessageDTO messageDTO : messages){
            Index index = new Index.Builder(messageDTO).index("messages").type("message").id(messageDTO.getId().toString()).build();
            result = jestClient.execute(index);
        }
        if(result.isSucceeded()){
            log.warn("Messages indexing has successful finished.");
            return retVal += messages.size();
        }else{
            log.warn("Messages indexing has failed.  " + result.getErrorMessage());
            return -1;
        }
    }

    public boolean addMessage(MessageDTO messageDTO){
        Index index = new Index.Builder(messageDTO).index("messages").type("message").id(messageDTO.getId().toString()).build();
        JestResult result;
        try {
            result = jestClient.execute(index);
            log.warn("Inserting message with id: " + messageDTO.getId() + " in index structure is succeeded: " + result.isSucceeded());
            return result.isSucceeded();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existMessage(int id) throws IOException {
        DocumentResult result = jestClient.execute(new Get.Builder("messages", id + "").build());
        return result.isSucceeded();
    }

    public boolean deleteMessage(int id){
        JestResult result;
        try {
            result = jestClient.execute(new Delete.Builder(id+"")
                    .index("messages")
                    .type("message")
                    .build());
            log.warn("Deleting message with id: " + id + " in index structure is succeeded: " + result.isSucceeded());
            if(result.isSucceeded())
                return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int indexAttachments(List<AttachmentDTO> attachments) throws IOException {
        File dataDir = new File("src/main/resources/files");
        List<File> files = Arrays.asList(dataDir.listFiles());

        PDFHandler pdfHandler = new PDFHandler();
        int retVal = 0;

        for(File file: files) {
            for(AttachmentDTO attachmentDTO: attachments){
                if (file.getName().endsWith(".pdf"))
                {
                    if(file.getName().equals(attachmentDTO.getName())) {
                        attachmentDTO.setBase64(pdfHandler.getIndexUnit(file).getText());
                    }
                }
            }
        }
        JestResult result = null;
        for(AttachmentDTO attachmentDTO: attachments){
            Index index = new Index.Builder(attachmentDTO).index("attachments").type("attachment").id(attachmentDTO.getId().toString()).build();
            result = jestClient.execute(index);
        }
        if(result.isSucceeded()) {
            log.warn("Indexing succeed");
            return retVal += attachments.size();
        }else {
            log.warn("Indexing failed");
            return -1;
        }
    }

    public boolean addAttachment(AttachmentDTO attachmentDTO){
        File dataDir = new File("C:\\Users\\Svetozar\\git\\ElasticSearch2021\\src\\main\\resources\\assets");
        List<File> files = Arrays.asList(dataDir.listFiles());
        PDFHandler pdfHandler = new PDFHandler();

        for(File file: files) {
            if (file.getName().endsWith(".pdf")) {
                if(file.getName().equals(attachmentDTO.getName())) {
                    attachmentDTO.setBase64(pdfHandler.getIndexUnit(file).getText());
                }
            }
        }
        Index index = new Index.Builder(attachmentDTO).index("attachments").type("attachment").id(attachmentDTO.getId().toString()).build();
        JestResult result;
        try {
            result = jestClient.execute(index);
            log.warn("Inserting attachment with id: " + attachmentDTO.getId() + " in index structure is succeeded: " + result.isSucceeded());
            return result.isSucceeded();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existAttachment(int id) throws IOException {
        DocumentResult d = jestClient.execute(new Get.Builder("attachments", id + "").build());
        return d.isSucceeded();
    }

    public int indexContacts(List<ContactDTO> contacts) throws IOException {
        JestResult result = null;
        int retVal = 0;
        for(ContactDTO contactDTO: contacts) {
            Index index = new Index.Builder(contactDTO).index("contacts").type("contact").id(contactDTO.getId() + "").build();
            result = jestClient.execute(index);
        }
        if(result.isSucceeded()) {
            log.warn("Indexing succeed");
            return retVal += contacts.size();
        }else {
            log.warn("Indexing failed");
            return -1;
        }
    }

    public boolean addContact(ContactDTO contactDTO){
        Index index = new Index.Builder(contactDTO).index("contacts").type("contact").id(contactDTO.getId()+"").build();
        JestResult result;
        try {
            result = jestClient.execute(index);
            log.warn("Indexing new contact with id: " + contactDTO.getId() + " is succeeded: " + result.isSucceeded());
            return result.isSucceeded();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existContact(int id) throws IOException {
        DocumentResult d = jestClient.execute(new Get.Builder("contacts", id+"").build());
        return d.isSucceeded();
    }


}
