package com.docudile.app.data.dao;

import com.docudile.app.data.entities.DocumentIndex;
import com.docudile.app.data.entities.File;

import java.util.List;

/**
 * Created by cicct on 3/11/2016.
 */
public interface DocumentIndexDao extends GenericDao<DocumentIndex> {
    public List<DocumentIndex> getID(List<File> documentID, Integer wordListID);
}
