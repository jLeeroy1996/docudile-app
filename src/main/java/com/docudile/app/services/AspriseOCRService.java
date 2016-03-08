package com.docudile.app.services;

import java.io.File;
import java.util.List;

/**
 * Created by franc on 3/6/2016.
 */
public interface AspriseOCRService {

    public List<String> doOCR(File file);

    public List<String> doOCRContent(File file);
}
