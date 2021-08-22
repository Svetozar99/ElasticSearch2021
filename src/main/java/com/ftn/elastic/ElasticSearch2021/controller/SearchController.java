package com.ftn.elastic.ElasticSearch2021.controller;

import com.ftn.elastic.ElasticSearch2021.dto.AttachmentDTO;
import com.ftn.elastic.ElasticSearch2021.dto.ContactDTO;
import com.ftn.elastic.ElasticSearch2021.dto.MessageDTO;
import com.ftn.elastic.ElasticSearch2021.lucene.model.AdvancedQuery;
import com.ftn.elastic.ElasticSearch2021.lucene.model.RequiredHighlight;
import com.ftn.elastic.ElasticSearch2021.lucene.model.SearchType;
import com.ftn.elastic.ElasticSearch2021.lucene.model.SimpleQuery;
import com.ftn.elastic.ElasticSearch2021.lucene.search.ResultRetriever;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/search")
public class SearchController {

    @PostMapping(value = "/term/messages", consumes = "application/json")
    public ResponseEntity<List<MessageDTO>> searchMessageTermQuery(@RequestBody SimpleQuery simpleQuery) throws Exception{
        QueryBuilder queryBuilder = com.ftn.elastic.ElasticSearch2021.lucene.search.QueryBuilder.buildQuery(SearchType.REGULAR, simpleQuery.getField(), simpleQuery.getValue());
        List<RequiredHighlight> rh = new ArrayList<>();
        rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
        List<MessageDTO> result = ResultRetriever.getResultsMessages(queryBuilder, rh);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/fuzzy/messages", consumes = "application/json")
    public ResponseEntity<List<MessageDTO>> searchMessageFuzzyQuery(@RequestBody SimpleQuery simpleQuery) throws Exception{
        QueryBuilder queryBuilder = com.ftn.elastic.ElasticSearch2021.lucene.search.QueryBuilder.buildQuery(SearchType.FUZZY, simpleQuery.getField(), simpleQuery.getValue());
        List<RequiredHighlight> rh = new ArrayList<>();
        rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
        List<MessageDTO> result = ResultRetriever.getResultsMessages(queryBuilder, rh);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/prefix/messages", consumes = "application/json")
    public ResponseEntity<List<MessageDTO>> searchMessagePrefixQuery(@RequestBody SimpleQuery simpleQuery) throws Exception{
        QueryBuilder queryBuilder = com.ftn.elastic.ElasticSearch2021.lucene.search.QueryBuilder.buildQuery(SearchType.PREFIX, simpleQuery.getField(), simpleQuery.getValue());
        List<RequiredHighlight> rh = new ArrayList<>();
        rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
        List<MessageDTO> result = ResultRetriever.getResultsMessages(queryBuilder, rh);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/range/messages", consumes = "application/json")
    public ResponseEntity<List<MessageDTO>> searchMessageRangeQuery(@RequestBody SimpleQuery simpleQuery) throws Exception{
        QueryBuilder queryBuilder = com.ftn.elastic.ElasticSearch2021.lucene.search.QueryBuilder.buildQuery(SearchType.RANGE, simpleQuery.getField(), simpleQuery.getValue());
        List<RequiredHighlight> rh = new ArrayList<>();
        rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
        List<MessageDTO> result = ResultRetriever.getResultsMessages(queryBuilder, rh);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/phrase/messages", consumes = "application/json")
    public ResponseEntity<List<MessageDTO>> searchMessagePhraseQuery(@RequestBody SimpleQuery simpleQuery) throws Exception{
        QueryBuilder queryBuilder = com.ftn.elastic.ElasticSearch2021.lucene.search.QueryBuilder.buildQuery(SearchType.PHRASE, simpleQuery.getField(), simpleQuery.getValue());
        List<RequiredHighlight> rh = new ArrayList<>();
        rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
        List<MessageDTO> result = ResultRetriever.getResultsMessages(queryBuilder, rh);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/boolean/messages", consumes = "application/json")
    public ResponseEntity<List<MessageDTO>> searchMessageBooleanQuery(@RequestBody AdvancedQuery advancedQuery) throws Exception{
        QueryBuilder query1 = com.ftn.elastic.ElasticSearch2021.lucene.search.QueryBuilder.buildQuery(SearchType.REGULAR, advancedQuery.getField1(), advancedQuery.getValue1());
        QueryBuilder query2 = com.ftn.elastic.ElasticSearch2021.lucene.search.QueryBuilder.buildQuery(SearchType.REGULAR, advancedQuery.getField2(), advancedQuery.getValue2());

        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        if(advancedQuery.getOperation().equalsIgnoreCase("AND")){
            builder.must(query1);
            builder.must(query2);
        }else if(advancedQuery.getOperation().equalsIgnoreCase("OR")){
            builder.should(query1);
            builder.should(query2);
        }else if(advancedQuery.getOperation().equalsIgnoreCase("NOT")){
            builder.must(query1);
            builder.mustNot(query2);
        }
        List<RequiredHighlight> rh = new ArrayList<>();
        rh.add(new RequiredHighlight(advancedQuery.getField1(), advancedQuery.getValue1()));
        rh.add(new RequiredHighlight(advancedQuery.getField2(), advancedQuery.getValue2()));
        List<MessageDTO> result = ResultRetriever.getResultsMessages(builder, rh);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/phrase/messages/attachment", consumes = "application/json")
    public ResponseEntity<List<MessageDTO>> searchMessageAttachmentsPhrase(@RequestBody SimpleQuery simpleQuery) throws Exception {
        QueryBuilder queryBuilder = com.ftn.elastic.ElasticSearch2021.lucene.search.QueryBuilder.buildQuery(SearchType.PHRASE, simpleQuery.getField(), simpleQuery.getValue());
        List<RequiredHighlight> rh = new ArrayList<>();
        rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
        List<AttachmentDTO> result = ResultRetriever.getResultAttachments(queryBuilder, rh);

        List<MessageDTO> resultMessages = new ArrayList<>();
        result.forEach(attachmentDTO -> {
            resultMessages.add(attachmentDTO.getMessage());
        });

        return new ResponseEntity<>(resultMessages, HttpStatus.OK);
    }

    @PostMapping(value = "/fuzzy/messages/attachment")
    public ResponseEntity<List<MessageDTO>> searchMessageAttachmentsFuzzy(@RequestBody SimpleQuery simpleQuery) throws Exception {
        QueryBuilder queryBuilder = com.ftn.elastic.ElasticSearch2021.lucene.search.QueryBuilder.buildQuery(SearchType.FUZZY, simpleQuery.getField(), simpleQuery.getValue());
        List<RequiredHighlight> rh = new ArrayList<>();
        rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
        List<AttachmentDTO> result = ResultRetriever.getResultAttachments(queryBuilder, rh);

        List<MessageDTO> resultMessages = new ArrayList<>();
        result.forEach(attachmentDTO -> {
            resultMessages.add(attachmentDTO.getMessage());
        });

        return new ResponseEntity<>(resultMessages, HttpStatus.OK);
    }

    @PostMapping(value = "/term/contacts", consumes = "application/json")
    public ResponseEntity<List<ContactDTO>> searchContactTermQuery(@RequestBody SimpleQuery simpleQuery) throws Exception{
        QueryBuilder queryBuilder = com.ftn.elastic.ElasticSearch2021.lucene.search.QueryBuilder.buildQuery(SearchType.REGULAR, simpleQuery.getField(), simpleQuery.getValue());
        List<RequiredHighlight> rh = new ArrayList<>();
        rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
        List<ContactDTO> result = ResultRetriever.getResultContacts(queryBuilder, rh);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/fuzzy/contacts", consumes = "application/json")
    public ResponseEntity<List<ContactDTO>> searchContactFuzzyQuery(@RequestBody SimpleQuery simpleQuery) throws Exception{
        QueryBuilder queryBuilder = com.ftn.elastic.ElasticSearch2021.lucene.search.QueryBuilder.buildQuery(SearchType.FUZZY, simpleQuery.getField(), simpleQuery.getValue());
        List<RequiredHighlight> rh = new ArrayList<>();
        rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
        List<ContactDTO> result = ResultRetriever.getResultContacts(queryBuilder, rh);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/prefix/contacts", consumes = "application/json")
    public ResponseEntity<List<ContactDTO>> searchContactPrefixQuery(@RequestBody SimpleQuery simpleQuery) throws Exception{
        QueryBuilder queryBuilder = com.ftn.elastic.ElasticSearch2021.lucene.search.QueryBuilder.buildQuery(SearchType.PREFIX, simpleQuery.getField(), simpleQuery.getValue());
        List<RequiredHighlight> rh = new ArrayList<>();
        rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
        List<ContactDTO> result = ResultRetriever.getResultContacts(queryBuilder, rh);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/phrase/contacts", consumes = "application/json")
    public ResponseEntity<List<ContactDTO>> searchContactPhraseQuery(@RequestBody SimpleQuery simpleQuery) throws Exception{
        QueryBuilder queryBuilder = com.ftn.elastic.ElasticSearch2021.lucene.search.QueryBuilder.buildQuery(SearchType.PHRASE, simpleQuery.getField(), simpleQuery.getValue());
        List<RequiredHighlight> rh = new ArrayList<>();
        rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
        List<ContactDTO> result = ResultRetriever.getResultContacts(queryBuilder, rh);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/boolean/contacts", consumes = "application/json")
    public ResponseEntity<List<ContactDTO>> searchContactBooleanQuery(@RequestBody AdvancedQuery advancedQuery) throws Exception{
        QueryBuilder query1 = com.ftn.elastic.ElasticSearch2021.lucene.search.QueryBuilder.buildQuery(SearchType.REGULAR, advancedQuery.getField1(), advancedQuery.getValue1());
        QueryBuilder query2 = com.ftn.elastic.ElasticSearch2021.lucene.search.QueryBuilder.buildQuery(SearchType.REGULAR, advancedQuery.getField2(), advancedQuery.getValue2());

        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        if(advancedQuery.getOperation().equalsIgnoreCase("AND")){
            builder.must(query1);
            builder.must(query2);
        }else if(advancedQuery.getOperation().equalsIgnoreCase("OR")){
            builder.should(query1);
            builder.should(query2);
        }else if(advancedQuery.getOperation().equalsIgnoreCase("NOT")){
            builder.must(query1);
            builder.mustNot(query2);
        }
        List<RequiredHighlight> rh = new ArrayList<>();
        rh.add(new RequiredHighlight(advancedQuery.getField1(), advancedQuery.getValue1()));
        rh.add(new RequiredHighlight(advancedQuery.getField2(), advancedQuery.getValue2()));
        List<ContactDTO> result = ResultRetriever.getResultContacts(builder, rh);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
