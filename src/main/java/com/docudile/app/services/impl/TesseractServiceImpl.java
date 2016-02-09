package com.docudile.app.services.impl;

import com.docudile.app.services.TesseractService;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;

/**
 * Created by franc on 2/9/2016.
 */
@Service("tesseractService")
@Transactional
public class TesseractServiceImpl implements TesseractService {

    public String doOCR(File file) {
        ITesseract tesseract = new Tesseract();
        if (verifyImage(file)) {
            try {
                return tesseract.doOCR(file);
            } catch (TesseractException e) {
                return null;
            }
        }
        return null;
    }

    private boolean verifyImage(File file) {
        String mimeType = new MimetypesFileTypeMap().getContentType(file);
        String type = mimeType.split("/")[0];
        return type.equals("image");
    }

}
