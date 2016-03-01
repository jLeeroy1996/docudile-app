package com.docudile.app.data.dao.impl;

import com.docudile.app.data.dao.GenericDao;
import com.docudile.app.data.dao.WordListCategoryDao;
import com.docudile.app.data.dao.WordListDao;
import com.docudile.app.data.entities.WordListCategory;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cicct on 3/2/2016.
 */
@Repository("wordListCategoryDao")
@Transactional
public class WordListCategoryDaoImpl extends GenericDaoImpl<WordListCategory> implements WordListCategoryDao {

    @Autowired
    WordListDao wordListDao = new WordListDaoImpl();

    public Float getVector(Integer categoryID, String word) {

        Query query = getCurrentSession().createQuery("from WordListCategory w where w.category.id = :categoryID and w.wordList.id = :wordListID");
        query.setParameter("categoryID", categoryID);
        query.setParameter("wordListID", wordListDao.getID(word));
        return (Float) query.uniqueResult();
    }
}
