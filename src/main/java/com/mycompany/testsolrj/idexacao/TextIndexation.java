package com.mycompany.testsolrj.idexacao;

import java.io.File;
import java.io.IOException;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

public class TextIndexation {

    private static final String HOST = "http://localhost:8983/solr";
    private SolrServer solr;

    public TextIndexation() {
        this.solr = new HttpSolrServer(HOST);
    }

    public void indexFilesSolrCell(String filename, String sorlId) throws IOException, SolrServerException {

        ContentStreamUpdateRequest up
                = new ContentStreamUpdateRequest("/update/extract");

        up.addFile(new File(filename), "application/octet-stream");
        up.setParam("literal.id", sorlId);
        up.setParam("uprefix", "attr_");
        up.setParam("fmap.content", "attr_content");

        up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);

        this.solr.request(up);
    }
    
    public void deleteIndex(String solrId) throws SolrServerException, IOException {
        this.solr.deleteById(solrId);
        this.solr.commit();
    }

    public String getContentById(String sorlId) throws SolrServerException {
        QueryResponse rsp = solr.query(new SolrQuery(sorlId));
        return String.valueOf(rsp);
    }

    public String getAllContent() throws SolrServerException {
        QueryResponse rsp = solr.query(new SolrQuery("*:*"));
        return String.valueOf(rsp);
    }

    public int getNumResutsById(String sorlId) throws SolrServerException {
        QueryResponse rsp = solr.query(new SolrQuery(sorlId));
        SolrDocumentList dcl = rsp.getResults();
        return dcl.size();
    }


}
