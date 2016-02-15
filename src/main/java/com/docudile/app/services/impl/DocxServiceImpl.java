package com.docudile.app.services.impl;

import com.docudile.app.services.DocxService;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by PaulRyan on 2/16/2016.
 */
@Service("docxService")
@Transactional
public class DocxServiceImpl implements DocxService {

    public File createDocx(String input, String filename) {
        String filepath = FilenameUtils.getBaseName(filename) + ".docx";
        File file = new File(filepath);
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(input);
        try {
            FileOutputStream output = new FileOutputStream(file);
            document.write(output);
            output.close();
            return file;
        } catch (Exception e) {
                e.printStackTrace();
        }
        return null;
    }
}
