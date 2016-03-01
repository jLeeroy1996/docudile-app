package com.docudile.app.data.dao;

import com.docudile.app.data.entities.File;
import com.docudile.app.data.entities.WordListDocument;

import java.util.List;

/**
 * Created by cicct on 2/25/2016.
 */
public interface WordListDocumentDao extends GenericDao<WordListDocument>{
    public List<WordListDocument> getID(List<File> documentID, Integer wordListID);
}
