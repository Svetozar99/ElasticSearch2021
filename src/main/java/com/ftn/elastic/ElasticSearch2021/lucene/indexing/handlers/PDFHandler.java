package com.ftn.elastic.ElasticSearch2021.lucene.indexing.handlers;

import com.ftn.elastic.ElasticSearch2021.lucene.model.IndexUnit;
import org.apache.lucene.document.DateTools;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class PDFHandler extends DocumentHandler {

    private static final Logger log = LoggerFactory.getLogger(PDFHandler.class);

    @Override
    public IndexUnit getIndexUnit(File file) {
        IndexUnit retVal = new IndexUnit();
        try {
            PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            String text = getText(parser);
            retVal.setText(text);

            PDDocument pdf = parser.getPDDocument();
            PDDocumentInformation info = pdf.getDocumentInformation();

            String title = "" + info.getTitle();
            retVal.setTitle(title);

            String keywords = "" + info.getKeywords();
            if(keywords != null){
                String[] splittedKeywords = keywords.split(" ");
                retVal.setKeywords(new ArrayList<>(Arrays.asList(splittedKeywords)));
            }

            retVal.setFileName(file.getCanonicalPath());

            String modificationDate= DateTools.dateToString(new Date(file.lastModified()),DateTools.Resolution.DAY);
            retVal.setFileDate(modificationDate);

            pdf.close();
        } catch (IOException e) {
            log.warn("An error has happened while converting document to .pdf!");
        }
        return retVal;
    }

    @Override
    public String getText(File file) {
        try {
            PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            PDFTextStripper textStripper = new PDFTextStripper();
            String text = textStripper.getText(parser.getPDDocument());
            return text;
        } catch (IOException e) {
            log.warn("An error has happened while converting document to .pdf!");
        }
        return null;
    }

    public String getText(PDFParser parser) {
        try {
            PDFTextStripper textStripper = new PDFTextStripper();
            String text = textStripper.getText(parser.getPDDocument());
            return text;
        } catch (IOException e) {
            log.warn("An error has happened while converting document to .pdf!");
        }
        return null;
    }
}
