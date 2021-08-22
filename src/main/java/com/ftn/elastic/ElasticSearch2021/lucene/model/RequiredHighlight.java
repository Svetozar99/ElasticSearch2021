package com.ftn.elastic.ElasticSearch2021.lucene.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequiredHighlight {

    private String fieldName;

    private String value;
}
