package com.docudile.app.data.dao;

import com.docudile.app.data.entities.WordListCategory;

/**
 * Created by cicct on 3/1/2016.
 */
public interface WordListCategoryDao extends GenericDao<WordListCategory> {
    public WordListCategory getVector(Integer categoryID, String word);
    public boolean isExist(Integer categoryID, String word);
}
