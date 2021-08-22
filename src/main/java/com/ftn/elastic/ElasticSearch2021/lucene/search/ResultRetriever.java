package com.ftn.elastic.ElasticSearch2021.lucene.search;

import com.ftn.elastic.ElasticSearch2021.dto.AttachmentDTO;
import com.ftn.elastic.ElasticSearch2021.dto.ContactDTO;
import com.ftn.elastic.ElasticSearch2021.dto.MessageDTO;
import com.ftn.elastic.ElasticSearch2021.lucene.model.RequiredHighlight;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultRetriever {

    private static final Logger log = LoggerFactory.getLogger(ResultRetriever.class);
    private static int maxHits = 10;

    private static JestClient client;

    static {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://localhost:9200")
                .multiThreaded(true)
                .build());
        ResultRetriever.client = factory.getObject();

    }

    public static void setMaxHits(int maxHits) {
        ResultRetriever.maxHits = maxHits;
    }

    public static int getMaxHits() {
        return ResultRetriever.maxHits;
    }

    public static List<MessageDTO> getResultsMessages(QueryBuilder query, List<RequiredHighlight> highlights){
        if(query == null){
            throw new IllegalArgumentException();
        }
        List<MessageDTO> results = new ArrayList<>();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);
        searchSourceBuilder.size(maxHits);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("from");
        highlightBuilder.field("to");
        highlightBuilder.field("subject");
        highlightBuilder.field("datetime");
        highlightBuilder.field("content");
        highlightBuilder.field("unread");
        highlightBuilder.field("account");
        highlightBuilder.field("account.username");
        highlightBuilder.field("cc");
        highlightBuilder.field("bcc");
        highlightBuilder.field("user");

        highlightBuilder.preTags("<spam style='color:red'>").postTags("</spam>");
        highlightBuilder.fragmentSize(200);
        searchSourceBuilder.highlighter(highlightBuilder);

        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex("messages")
                .addType("message")
                .build();

        SearchResult result;
        try {
            result = client.execute(search);
            if(result.isSucceeded()) {
                log.warn("Search is succeed");
            }else {
                log.warn("An error has happened during searching: " + result.getErrorMessage());

            }
            List<SearchResult.Hit<MessageDTO, Void>> hits = result.getHits(MessageDTO.class);
            MessageDTO resultData = new MessageDTO();

            for (SearchResult.Hit<MessageDTO, Void> sd : hits) {
                String highlight = "";
                for (String hf : sd.highlight.keySet() ) {
                    for (RequiredHighlight rh : highlights) {
                        if(hf.equals(rh.getFieldName())){
                            highlight += sd.highlight.get(hf).toString();
                        }
                    }
                }

                resultData.setContent(sd.source.getContent());
                resultData.setSubject(sd.source.getSubject());
                resultData.setDateTime(sd.source.getDateTime());
                resultData.setFrom(sd.source.getFrom());
                resultData.setAccount(sd.source.getAccount());
                resultData.setFolder(sd.source.getFolder());
                resultData.setTo(sd.source.getTo());
                resultData.setCc((sd.source.getCc() != null)? sd.source.getCc() : null);
                resultData.setBcc((sd.source.getBcc() != null)? sd.source.getBcc() : null);

                results.add(resultData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static List<AttachmentDTO> getResultAttachments(QueryBuilder query, List<RequiredHighlight> highlights){
        if(query == null){
            throw new IllegalArgumentException();
        }
        List<AttachmentDTO> results = new ArrayList<>();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);
        searchSourceBuilder.size(maxHits);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("id");
        highlightBuilder.field("message");
        highlightBuilder.field("content");
        highlightBuilder.field("mimeType");
        highlightBuilder.field("name");

        highlightBuilder.preTags("<spam style='color:red'>").postTags("</spam>");
        highlightBuilder.fragmentSize(200);
        searchSourceBuilder.highlighter(highlightBuilder);
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex("attachments")
                .addType("attachment")
                .build();
        SearchResult result;
        try {
            result = client.execute(search);
            if(result.isSucceeded()) {
                log.warn("Search succeed");
            }else {
                log.warn("An error has occurred during searching: " + result.getErrorMessage());

            }
            List<SearchResult.Hit<AttachmentDTO, Void>> hits = result.getHits(AttachmentDTO.class);
            AttachmentDTO resultData = new AttachmentDTO();

            for (SearchResult.Hit<AttachmentDTO, Void> sd : hits) {
                String highlight = "";
                for (String hf : sd.highlight.keySet() ) {
                    for (RequiredHighlight rh : highlights) {
                        if(hf.equals(rh.getFieldName())){
                            highlight += sd.highlight.get(hf).toString();
                        }
                    }
                }
                resultData.setId(sd.source.getId());
                resultData.setBase64(sd.source.getBase64());
                resultData.setMessage(sd.source.getMessage());
                resultData.setName(sd.source.getName());
                resultData.setMimeType(sd.source.getMimeType());
                results.add(resultData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static List<ContactDTO> getResultContacts(QueryBuilder query, List<RequiredHighlight> highlights) {
        if(query == null){
            throw new IllegalArgumentException();
        }

        List<ContactDTO> results = new ArrayList<>();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);
        searchSourceBuilder.size(maxHits);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("firstName");
        highlightBuilder.field("lastName");
        highlightBuilder.field("email");
        highlightBuilder.field("note");

        highlightBuilder.preTags("<spam style='color:red'>").postTags("</spam>");
        highlightBuilder.fragmentSize(200);
        searchSourceBuilder.highlighter(highlightBuilder);
        Search search = new Search.Builder(searchSourceBuilder.toString())
                // multiple index or types can be added.
                .addIndex("contacts")
                .addType("contact")
                .build();
        SearchResult result;

        try {
            result = client.execute(search);
            if(result.isSucceeded()) {
                log.warn("Search succeed");
            }else {
                log.warn("An error has occurred during searching: " + result.getErrorMessage());

            }
            List<SearchResult.Hit<ContactDTO, Void>> hits = result.getHits(ContactDTO.class);
            ContactDTO rd = new ContactDTO();

            for (SearchResult.Hit<ContactDTO, Void> sd : hits) {
                String highlight = "";
                for (String hf : sd.highlight.keySet() ) {
                    for (RequiredHighlight rh : highlights) {
                        if(hf.equals(rh.getFieldName())){
                            highlight += sd.highlight.get(hf).toString();
                        }
                    }
                }
                rd.setFirstName(sd.source.getFirstName());
                rd.setLastName(sd.source.getLastName());
                rd.setEmail(sd.source.getEmail());
                rd.setNote(sd.source.getNote());
                results.add(rd);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

}
