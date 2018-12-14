package com.donwae.market.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * TODO
 *
 * @auther Jeremy
 * 2018/12/12 上午11:07
 */
public class SearchIndex {

    public static void main(String[] args){
        SearchIndex si = new SearchIndex();
        si.search();
    }

    public void search() {
        try {
            Directory directory = FSDirectory.open(Paths.get(CreateIndex.indexDir));
            IndexReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);

            QueryParser queryParser = new QueryParser("content", new StandardAnalyzer());

            Query query = queryParser.parse("form");

            TopDocs topDocs = searcher.search(query, 10);

            ScoreDoc[] scoreDocs = topDocs.scoreDocs;

            for (ScoreDoc sc : scoreDocs) {
                int docId = sc.doc;
                Document document = reader.document(docId);
                System.out.println(document.get("filename"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
