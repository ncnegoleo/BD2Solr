package com.mycompany.testsolrj.idexacao;

import java.io.File;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCRConversion {

    public String conversion(String path) throws TesseractException {
        File imageFile = new File(path);
        Tesseract instance = Tesseract.getInstance();

        return instance.doOCR(imageFile);
    }

}
