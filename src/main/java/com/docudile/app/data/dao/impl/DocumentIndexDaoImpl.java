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

    public List<DocumentIndex> getIDwithCount(List<File> documentID, Integer wordListID) {
        Query query;
        List<DocumentIndex> documentIndexList = new ArrayList<DocumentIndex>();
        for (int x = 0; x < documentID.size(); x++) {
            query = getCurrentSession().createQuery("from DocumentIndex w where w.file.id = :documentID and w.wordList.id = :wordListID and where w.count > 0");
            query.setParameter("documentID", documentID.get(x).getId());
            query.setParameter("wordListID", wordListID);
            documentIndexList.add((DocumentIndex) query.uniqueResult());
        }
        return documentIndexList;
    }

    public List<DocumentIndex> getID(List<File> documentID){
        Query query;
        List<DocumentIndex> documentIndexList = new ArrayList<DocumentIndex>();
        for (int x = 0; x < documentID.size(); x++) {
            query = getCurrentSession().createQuery("from DocumentIndex w where w.file.id = :documentID");
            query.setParameter("documentID", documentID.get(x).getId());
            documentIndexList.add((DocumentIndex) query.uniqueResult());
        }
        return documentIndexList;
    }

    public List<Integer[]> getID(List<File> documentID, List<Integer> wordListID) {
        Query query;
        List<Integer[]> finalList = new ArrayList<>();
        DocumentIndex sample = new DocumentIndex();
        int count= 0;
        for (int x = 0; x < documentID.size(); x++) {
            Integer[] temp = new Integer[wordListID.size()+2];
            temp[0] = documentID.get(x).getId();
            count = 0;
            for(int y =0;y<wordListID.size();y++) {
                sample = new DocumentIndex();
                query = getCurrentSession().createQuery("from DocumentIndex w where w.file.id = :documentID and w.wordList.id = :wordListID");
                query.setParameter("documentID", documentID.get(x).getId());
                query.setParameter("wordListID", wordListID.get(y));
                sample = (DocumentIndex) query.uniqueResult();
                if(sample == null){
                    temp[y+2] = 0;
                }else {
                    temp[y + 2] = sample.getCount();
                    count++;
                }
            }
            temp[1] = count;
            finalList.add(temp);
        }
        return finalList;
    }

}