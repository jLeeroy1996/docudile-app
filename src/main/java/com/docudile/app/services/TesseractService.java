package com.docudile.app.services;

import java.io.File;
import java.util.List;

/**
 * Created by franc on 2/9/2016.
 */
public interface TesseractService {

    public List<String> doOCR(File file);

}
