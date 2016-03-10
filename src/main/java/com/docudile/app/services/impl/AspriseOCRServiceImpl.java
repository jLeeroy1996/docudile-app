package com.docudile.app.services.impl;

import com.asprise.ocr.Ocr;
import com.docudile.app.services.AspriseOCRService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by franc on 3/6/2016.
 */
@Service("aspriseOCRService")
@Transactional
public class AspriseOCRServiceImpl implements AspriseOCRService {

    @Override
    public List<String> doOCR(File file) {
        System.out.println(file.getName());
        if (verifyImage(file)) {
            Ocr.setUp();
            Ocr ocr = new Ocr();
            ocr.startEngine("eng", Ocr.SPEED_SLOW);
            String result = ocr.recognize(new File[]{file}, Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);
            ocr.stopEngine();
            return Arrays.asList(result.split("\n"));
        }
        return null;
    }

    @Override
    public List<String> doOCRContent(File file) {
        System.out.println(file.getName());
        if (verifyImage(file)) {
            Ocr.setUp();
            Ocr ocr = new Ocr();
            ocr.startEngine("eng", Ocr.SPEED_SLOW);
            String result = ocr.recognize(new File[]{file}, Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);
            ocr.stopEngine();
            List<String> finalList = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(result," ~`!@#$%^&*()-=_+[]{};'\\:|,./<>?");
            while(st.hasMoreTokens()){
                if(st.nextToken().length()>1) {
                    finalList.add(st.nextToken());
                }
            }
            return finalList;
        }
        return null;
    }


    private boolean verifyImage(File file) {
        String mimeType = new MimetypesFileTypeMap().getContentType(file);
        String type = mimeType.split("/")[0];
        return type.equals("image");
    }

}
