package com.docudile.app.data.dao.impl;

import com.docudile.app.data.dao.WordListDao;
import com.docudile.app.data.entities.WordList;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cicct on 2/25/2016.
 */

@Repository("wordListDao")
@Transactional
public class WordListDaoImpl extends GenericDaoImpl<WordList> implements WordListDao {

    public WordList getID(String word) {
        Query query = getCurrentSession().createQuery("from WordList w where  w.word = :word");
        query.setParameter("word",word);
        return (WordList) query.uniqueResult();
    }
}
