package com.docudile.app.services;

/**
 * Created by FrancAnthony on 3/1/2016.
 */
public interface TfIdfService {

    public boolean process(String dataPath, String savePath);

    public String search(String query, String dataPath);

}
