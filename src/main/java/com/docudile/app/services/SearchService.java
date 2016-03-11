package com.docudile.app.services;

import com.docudile.app.data.entities.DocumentIndex;

import java.util.List;

/**
 * Created by cicct on 2/25/2016.
 */
public interface SearchService {

    public List<DocumentIndex> search(Integer userId, String searchString);

    public void generateWordList(List<String> words, Integer userId);

    public void generateDocIndex(List<String> words, Integer fileID);

}
