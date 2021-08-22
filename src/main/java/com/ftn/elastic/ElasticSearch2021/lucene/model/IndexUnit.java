package com.ftn.elastic.ElasticSearch2021.lucene.model;

import io.searchbox.annotations.JestId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IndexUnit {

    private String text;

    private String title;

    private List<String> keywords = new ArrayList<>();

    @JestId
    private String fileName;

    private String fileDate;

    public Document getLuceneDocument(){
        Document retVal = new Document();
        retVal.add(new TextField("text", text, Field.Store.NO));
        retVal.add(new TextField("title", title, Field.Store.YES));
        for (String keyword : keywords) {
            retVal.add(new TextField("keyword", keyword, Field.Store.YES));
        }
        retVal.add(new StringField("filename", fileName, Field.Store.YES));
        retVal.add(new TextField("fileDate",fileDate, Field.Store.YES));
        return retVal;
    }

    public XContentBuilder getXContentBuilder() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("text", text)
                .field("title", title)
                .field("fileName", fileName)
                .field("fileDate", fileDate)
                .field("keyword", Arrays.toString(keywords.toArray()))
                .endObject();
        return builder;
    }
}
