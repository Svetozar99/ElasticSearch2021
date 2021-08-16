package com.ftn.elastic.ElasticSearch2021.utility;

import com.ftn.elastic.ElasticSearch2021.dto.AccountDTO;
import com.ftn.elastic.ElasticSearch2021.model.Account;
import com.ftn.elastic.ElasticSearch2021.model.Attachment;
import com.ftn.elastic.ElasticSearch2021.model.Message;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.AccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

@Component
public class MailUtil {

    private final String MSG_PATH = "C:/Users/Svetozar/git/ElasticSearch2021/src/main/resources/assets/";

    @Autowired
    private AccountServiceInterface accountServiceInterface;

    private JavaMailSenderImpl getJavaMailSender(Account account){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(account.getSmtpAddress());
        javaMailSender.setPort(account.getSmtpPort());
        javaMailSender.setUsername(account.getUsername());
        javaMailSender.setPassword(account.getPassword());

        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.imap.host", account.getInServerAddress());
        properties.put("mail.imap.port", account.getInServerPort());
        properties.put("mail.imap.ssl.enable", "true");

        javaMailSender.setJavaMailProperties(properties);

        return javaMailSender;
    }

    public boolean sendMessage(Message message){
        try{
            JavaMailSenderImpl mailSender = getJavaMailSender(message.getAccount());

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setTo(message.getTo());
            messageHelper.setFrom(message.getFrom());
            if(message.getCc() != null && !message.getCc().equals("")){
                messageHelper.setCc(message.getCc());
            }
            if(message.getBcc() != null && !message.getBcc().equals("")){
                messageHelper.setBcc(message.getBcc());
            }
            messageHelper.setSubject(message.getSubject());
            messageHelper.setText(message.getContent());

            if(message.getAttachments() != null && !message.getAttachments().isEmpty()){
                message.getAttachments().stream().forEach(attachment -> {
                    if(attachment.getPath() != null && !attachment.getPath().isEmpty()){
                        byte[] attachmentData = FileUtil.readBytes(attachment.getPath());
                        if(attachmentData != null){
                            ByteArrayDataSource dataSource = new ByteArrayDataSource(attachmentData, attachment.getMimeType());
                            try{
                                messageHelper.addAttachment(attachment.getName(), dataSource);
                            }catch (MessagingException e){
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
            mailSender.send(mimeMessage);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("ERRORRR");
            return false;
        }
    }

//    public List<Message> pullMessages(Account account){
//        try{
////            LocalDateTime syncTime = account.getSyncTime();
//            Store store = null;
//            Session session;
//
//            if(account.getInServerType() == 1){
//                Properties props = new Properties();
//                props.put("mail.store.protocol", "pop3");
//                props.put("mail.pop3.host", account.getInServerAddress());
//                props.put("mail.pop3.port", account.getInServerPort());
//                props.put("mail.pop3.ssl.enable", "true");
//                props.put("mail.pop3.ssl.trust", "*");
//                session = Session.getDefaultInstance(props);
//                store = session.getStore("pop3s");
//
//            }else if (account.getInServerType() == 2){
//                Properties props = new Properties();
//                props.put("mail.store.protocol", "imap");
//                props.put("mail.imap.host", account.getInServerAddress());
//                props.put("mail.imap.port", account.getInServerPort());
//                props.put("mail.imap.starttls.enable", "true");
//                props.put("mail.imap.partialfetch", "false");
//                props.setProperty("mail.imap.partialfetch", "false");
//                props.setProperty("mail.imaps.partialfetch", "false");
//                props.put("mail.imaps.ssl.trust", "*");
//                session = Session.getDefaultInstance(props);
//                store = session.getStore("imaps");
//            }
//
//            store.connect(account.getInServerAddress(), account.getUsername(), account.getPassword());
//
//            Folder inbox = store.getFolder("INBOX");
//            inbox.open(Folder.READ_ONLY);
////            account.setSyncTime(LocalDateTime.now());
//            accountServiceInterface.update(account.getId(), new AccountDTO(account));
//            javax.mail.Message[] messages = inbox.getMessages();
//            List<Message> loadMessages = new ArrayList<>();
//
//            if(syncTime == null){
//                for(int i = (messages.length - 1); i > -1; i--){
//                    javax.mail.Message javaxMessage = messages[i];
//                    try{
//                        Message message = javaxMessageConvertor(javaxMessage);
//                        message.setAccount(account);
//                        loadMessages.add(message);
//                    }catch(Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }else{
//                for(int i = (messages.length - 1); i > -1; i--){
//                    javax.mail.Message javaxMessage = messages[i];
//                    System.out.println("Message new 1 step ");
//                    if(LocalDateTime.ofInstant(javaxMessage.getSentDate().toInstant(), ZoneId.systemDefault()).isAfter(syncTime)){
//                        try {
//                            Message message = javaxMessageConvertor(javaxMessage);
//                            if (!message.getFrom().contains(account.getUsername())) {
//                                message.setAccount(account);
//                                System.out.println("Message new 2 step "+  message.getSubject());
//                                loadMessages.add(message);
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//
//            inbox.close(false);
//            store.close();
//
//            return loadMessages;
//
//        }catch (NoSuchProviderException e){
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    private Message javaxMessageConvertor(javax.mail.Message message) throws MessagingException, IOException{
//        Message msg = new Message();
//        Address[] addresses;
//        StringBuilder sb;
//
//        msg.setFrom(message.getFrom()[0].toString());
//
//        if((addresses = message.getRecipients(javax.mail.Message.RecipientType.TO)) != null){
//            sb = new StringBuilder();
//            for(int i = 0; i < addresses.length; i++){
//                if(i > 0){
//                    sb.append(", ");
//                }
//                sb.append(addresses[i].toString());
//            }
//            msg.setTo(sb.toString());
//        }
//        if((addresses = message.getRecipients(javax.mail.Message.RecipientType.CC)) != null){
//            sb = new StringBuilder();
//            for(int i = 0; i < addresses.length; i++){
//                if(i > 0){
//                    sb.append(", ");
//                }
//                sb.append(addresses[i].toString());
//            }
//            msg.setCc(sb.toString());
//        }
//        if((addresses = message.getRecipients(javax.mail.Message.RecipientType.BCC)) != null){
//            sb = new StringBuilder();
//            for(int i = 0; i < addresses.length; i++){
//                if(i > 0){
//                    sb.append(", ");
//                }
//                sb.append(addresses[i].toString());
//            }
//            msg.setBcc(sb.toString());
//        }
//
//        msg.setDateTime(LocalDateTime.ofInstant(message.getSentDate().toInstant(), ZoneId.systemDefault()));
//        msg.setSubject(message.getSubject());
//        msg.setUnread(!message.isSet(Flags.Flag.SEEN));
//
//        if(message.getContentType().toLowerCase().contains("text")){
//            msg.setContent(message.getContent().toString().replaceAll("\\<.*?\\>", ""));
//        }else if(message.getContentType().toLowerCase().contains("multipart")){
//            Multipart multipart = (Multipart) message.getContent();
//            for(int i = 0; i < multipart.getCount(); i++){
//                processMultiPart(multipart.getBodyPart(i), msg);
//            }
//        }
//        return msg;
//    }
//
//
//    private void processMultiPart(Part bodyPart, Message msg) throws MessagingException, IOException{
//        if(bodyPart.getContentType().toLowerCase().contains("text")) {
//            if (msg.getContent() != null)
//                msg.setContent(msg.getContent() + bodyPart.getContent().toString().replaceAll("\\<.*?\\>", ""));
//            else
//            msg.setContent(bodyPart.getContent().toString().replaceAll("\\<.*?\\>", ""));
//        }
//        else if (bodyPart.getContentType().toLowerCase().contains("multipart")){
//            Multipart mp = (Multipart) bodyPart.getContent();
//            for(int i = 0; i < mp.getCount(); i++){
//                processMultiPart(mp.getBodyPart(i), msg);
//            }
//        }else {
//            if (bodyPart.getDisposition() != null && bodyPart.getDisposition().equalsIgnoreCase(Part.ATTACHMENT)
//                    && bodyPart.getFileName() != null && !bodyPart.getFileName().isEmpty()){
//                    var inputStream = bodyPart.getInputStream();
//                    var file = new File(MSG_PATH + bodyPart.getFileName());
//                if(FileUtil.saveBytes(inputStream, file)){
//                    Attachment attachment = new Attachment();
//                    attachment.setName(bodyPart.getFileName());
//                    attachment.setMimeType(bodyPart.getContentType().split(";")[0]);
//                    attachment.setPath(MSG_PATH + bodyPart.getFileName());
//                    attachment.setMessage(msg);
//
//                    msg.getAttachments().add(attachment);
//                }
//            }
//        }
//    }
}
