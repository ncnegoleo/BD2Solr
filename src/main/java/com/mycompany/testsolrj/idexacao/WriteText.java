package com.mycompany.testsolrj.idexacao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class WriteText {

    private File file;

    public WriteText(File file) {
        this.file = file;
    }

    public WriteText(String filePath) {
        this.file = new File(filePath);
    }

    public void write(String content) throws FileNotFoundException, IOException {
        OutputStream os;

        os = new FileOutputStream(this.file);
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);

        bw.write(content);

        bw.close();

    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
