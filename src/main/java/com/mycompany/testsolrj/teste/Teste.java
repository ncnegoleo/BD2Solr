package com.mycompany.testsolrj.teste;

import com.mycompany.testsolrj.idexacao.OCRConversion;
import com.mycompany.testsolrj.idexacao.TextIndexation;
import com.mycompany.testsolrj.idexacao.WriteTextPDF;
import java.io.FileNotFoundException;
import java.io.IOException;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.solr.client.solrj.SolrServerException;

public class Teste {

    public static void main(String[] args) {

        indexTextFile("leo.pdf");
    }

    public static void indexTextFile(String filePath) {
        TextIndexation ti;

        try {
            ti = new TextIndexation();
            ti.indexFilesSolrCell(filePath, filePath);

            // verifica se o arquivo indexado tem conteudo
            if (ti.getNumResutsById(filePath) <= 0) {
                
                //deleta o conteudo indexado.
                ti.deleteIndex(filePath);

                OCRConversion ocrc = new OCRConversion();
                String newContent;
                try {
                    // tenta usar OCR, caso o arquivo tenha texto em imagem
                    // adiciona o novo conteudo em uma string
                    newContent = ocrc.conversion(filePath);
                    
                    // Escreve o novo conteudo em um arquivo de texto auxiliar
                    WriteTextPDF wt = new WriteTextPDF("auxText.dpf");
                    
                    try {
                        wt.write(newContent);
                    } catch (FileNotFoundException | COSVisitorException ex) {
                        System.out.println("erro ao criar arquivo auxiliar");
                    }
                    
                    System.out.println(wt.getFile().getName());
                    
                    // Tenta indexar novamente o arquivo, convertido agora
                    ti.indexFilesSolrCell(wt.getFile().getName(), filePath);
                    
                } catch (TesseractException ex) {
                    System.out.println("erro ao converter o arquivo com OCR");
                }

            }
            
            System.out.println(ti.getContentById(filePath));
        } catch (IOException | SolrServerException e) {
            System.out.println("erro ao indexar o arquivo");
        }

    }
}
