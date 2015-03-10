package com.mycompany.testsolrj.idexacao;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class WriteTextPDF {

    private File file;

    public WriteTextPDF(File file) {
        this.file = file;
    }

    public WriteTextPDF(String filePath) {
        this.file = new File(filePath);
    }

    public void write(String content) throws IOException, COSVisitorException {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            PDFont font = PDType1Font.HELVETICA_BOLD;
            
            PDPageContentStream pcs = new PDPageContentStream(document, page);
            
            pcs.beginText();
            pcs.setFont(font, 12);
            pcs.moveTextPositionByAmount(100, 700);
            pcs.drawString(content);
            pcs.endText();
            
            pcs.close();
            
            document.save(file);
        
//        OutputStream os;
//
//        os = new FileOutputStream(this.file);
//        OutputStreamWriter osw = new OutputStreamWriter(os);
//        BufferedWriter bw = new BufferedWriter(osw);
//
//        bw.write(content);
//
//        bw.close();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
