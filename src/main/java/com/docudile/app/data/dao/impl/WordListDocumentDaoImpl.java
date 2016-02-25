package com.docudile.app.data.dao.impl;

import com.docudile.app.data.dao.WordListDocumentDao;
import com.docudile.app.data.entities.File;
import com.docudile.app.data.entities.WordListDocument;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cicct on 2/25/2016.
 */
@Repository("wordListDocument")
@Transactional
public class WordListDocumentDaoImpl extends GenericDaoImpl<WordListDocument> implements WordListDocumentDao {
    public List<WordListDocument> getID(List<File> documentID, Integer wordListID) {
        Query query;
        List<WordListDocument> wordListDocumentList = new ArrayList<WordListDocument>();
        for (int x = 0; x < documentID.size(); x++) {
            query = getCurrentSession().createQuery("from WordListDocument w where w.file.id = :documentID and w.wordList.id = :wordListID");
            query.setParameter("documentID", documentID.get(x).getId());
            query.setParameter("wordListID", wordListID);
            wordListDocumentList.add((WordListDocument) query.uniqueResult());
        }
        return wordListDocumentList;
    }
}
