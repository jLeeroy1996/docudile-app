package com.docudile.app.services;

import java.io.File;
import java.util.List;

/**
 * Created by PaulRyan on 2/16/2016.
 */
public interface DocxService {

    public File createDocx(String input, String filename);

    public List<String> readDocx(File file);

    public List<String> readDocxContent(File file);
}
