package com.ftn.elastic.ElasticSearch2021.lucene.indexing.handlers;

import com.ftn.elastic.ElasticSearch2021.lucene.model.IndexUnit;

import java.io.File;

public abstract class DocumentHandler {

    public abstract IndexUnit getIndexUnit(File file);
    public abstract String getText(File file);
}
