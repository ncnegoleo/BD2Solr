/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testsolrj;

import java.io.File;
import java.io.IOException;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;

/**
 *
 * @author pc
 */
public class Teste {

    public static void main(String[] args) {
        try {
            indexFilesSolrCell("pdf2.pdf", "pdf2.pdf");
        } catch (IOException | SolrServerException e) {
            System.out.println(e.toString());
        }

    }

    public static void indexFilesSolrCell(String filename, String sorlId) throws IOException, SolrServerException {

        String urlString = "http://localhost:8983/solr";
        SolrServer solr = new HttpSolrServer(urlString);

        ContentStreamUpdateRequest up
                = new ContentStreamUpdateRequest("/update/extract");

        up.addFile(new File(filename), "application/octet-stream");

        up.setParam("literal.id", sorlId);
        up.setParam("uprefix", "attr_");
        up.setParam("fmap.content", "attr_content");

        up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);

        solr.request(up);

        QueryResponse rsp = solr.query(new SolrQuery("*:*"));

        System.out.println(rsp);
    }

}
