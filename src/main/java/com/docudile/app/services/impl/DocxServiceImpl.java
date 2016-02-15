package com.docudile.app.services.impl;

import com.docudile.app.services.DocxService;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;

/**
 * Created by PaulRyan on 2/16/2016.
 */
@Service("docxService")
@Transactional
public class DocxServiceImpl implements DocxService {

    public void createDocx(String input, String filename) {
        String basename = FilenameUtils.getBaseName(filename) + ".docx";
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(input);
        try {
            FileOutputStream output = new FileOutputStream(filename);
            document.write(output);
            output.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
}
