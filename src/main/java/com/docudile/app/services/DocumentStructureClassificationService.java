package com.docudile.app.services;

import com.docudile.app.services.impl.DocumentStructureClassificationServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * Created by FrancAnthony on 3/1/2016.
 */
public interface DocumentStructureClassificationService {

    public Map<Integer, String> tag(List<String> lines);

    public String classify(String query);

    public boolean delete(String path);

}
