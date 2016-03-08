package com.docudile.app.services;

import com.docudile.app.data.entities.WordListDocument;

import java.util.List;
import java.util.Set;

/**
 * Created by cicct on 2/25/2016.
 */
public interface SearchService {

    public List<WordListDocument> search(Integer userId, String searchString);

}
