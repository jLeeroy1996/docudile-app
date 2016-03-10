package com.docudile.app.services.impl;

import com.docudile.app.services.DocxService;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

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

    public List<String> readDocx(File file) {
        XWPFWordExtractor extractor = null;
        try {
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            XWPFDocument document = new XWPFDocument(fis);
            extractor = new XWPFWordExtractor(document);
            String words = extractor.getText();
            String[] wordArray = words.split("\n");
            List<String> wordList = Arrays.asList(wordArray);
            return wordList;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<String> readDocxContent(File file) {
        XWPFWordExtractor extractor = null;
        try {
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            XWPFDocument document = new XWPFDocument(fis);
            extractor = new XWPFWordExtractor(document);
            List<String> words = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(extractor.getText()," ~`!@#$%^&*()-=_+[]{};'\\:|,./<>?");
            while(st.hasMoreTokens()){
                if(st.nextToken().length()>1) {
                    words.add(st.nextToken());
                }
            }
            return words;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
