package com.ftn.elastic.ElasticSearch2021.lucene.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdvancedQuery {

    private String field1;

    private String value1;

    private String field2;

    private String value2;

    private String operation;
}
