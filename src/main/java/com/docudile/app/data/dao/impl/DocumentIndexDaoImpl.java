package com.docudile.app.data.dao.impl;

import com.docudile.app.data.dao.DocumentIndexDao;
import com.docudile.app.data.entities.DocumentIndex;
import com.docudile.app.data.entities.File;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cicct on 3/11/2016.
 */
@Repository("documentIndex")
@Transactional
public class DocumentIndexDaoImpl extends GenericDaoImpl<DocumentIndex> implements DocumentIndexDao{
    public List<DocumentIndex> getID(List<File> documentID, Integer wordListID) {
        Query query;
        List<DocumentIndex> documentIndexList = new ArrayList<DocumentIndex>();
        for (int x = 0; x < documentID.size(); x++) {
            query = getCurrentSession().createQuery("from DocumentIndex w where w.file.id = :documentID and w.wordList.id = :wordListID");
            query.setParameter("documentID", documentID.get(x).getId());
            query.setParameter("wordListID", wordListID);
            documentIndexList.add((DocumentIndex) query.uniqueResult());
        }
        return documentIndexList;
    }
}