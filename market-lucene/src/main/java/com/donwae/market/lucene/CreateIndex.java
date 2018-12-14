package com.donwae.market.lucene;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * 建立索引
 * @auther Jeremy
 * 2018/12/11 下午9:17
 */
public class CreateIndex {

    public static final String indexDir = "/Users/goldwindzty/Documents/ITTest/solr/index";

    public static final String dataDir = "/Users/goldwindzty/Documents/ITTest/solr/data/";

    public static void main(String[] args){
        CreateIndex ci = new CreateIndex();
        ci.createIndex();
    }

    public void createIndex(){

        try {
            Directory directory = FSDirectory.open(Paths.get(indexDir));
            // 创建分词器
            Analyzer analyzer = new StandardAnalyzer();

            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

            IndexWriter writer = new IndexWriter(directory, config);

            File file = new File(dataDir);
            File[] files = file.listFiles();
            for(File f: files){
                Document document = new Document();
                document.add(new StringField("filename", f.getName(), Field.Store.YES));
                document.add(new TextField("content", FileUtils.readFileToString(f, "UTF-8"), Field.Store.YES));
                document.add(new SortedNumericDocValuesField("lastModify", f.lastModified()));
                writer.addDocument(document);
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
