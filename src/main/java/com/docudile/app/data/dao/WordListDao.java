package com.docudile.app.data.dao;

import com.docudile.app.data.entities.WordList;

import java.util.List;

/**
 * Created by cicct on 2/25/2016.
 */

public interface WordListDao {

    public WordList getID(String word);

    public List<String> getWords();
}
