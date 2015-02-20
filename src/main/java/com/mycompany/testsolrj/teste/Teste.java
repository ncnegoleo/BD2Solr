package com.mycompany.testsolrj.teste;

import com.mycompany.testsolrj.idexacao.OCRConversion;
import com.mycompany.testsolrj.idexacao.TextIndexation;
import com.mycompany.testsolrj.idexacao.WriteText;
import java.io.IOException;
import net.sourceforge.tess4j.TesseractException;
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
                    WriteText wt = new WriteText("auxText.txt");
                    wt.write(newContent);
                    
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
