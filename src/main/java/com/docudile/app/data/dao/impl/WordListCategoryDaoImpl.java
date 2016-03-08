package com.docudile.app.data.dao.impl;

import com.docudile.app.data.dao.GenericDao;
import com.docudile.app.data.dao.WordListCategoryDao;
import com.docudile.app.data.dao.WordListDao;
import com.docudile.app.data.entities.WordList;
import com.docudile.app.data.entities.WordListCategory;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cicct on 3/2/2016.
 */
@Repository("wordListCategoryDao")
@Transactional
public class WordListCategoryDaoImpl extends GenericDaoImpl<WordListCategory> implements WordListCategoryDao {

    @Autowired
    WordListDao wordListDao = new WordListDaoImpl();

    public WordListCategory getVector(Integer categoryID, String word) {

        Query query = getCurrentSession().createQuery("from WordListCategory w where w.category.id = :categoryID and w.wordList.id = :wordListID");
        query.setParameter("categoryID", categoryID);
        if(wordListDao.getID(word) == null){
            return null;

        }
        query.setParameter("wordListID", wordListDao.getID(word).getId());

        if( query.uniqueResult() == null){

            return null;

        }
        return (WordListCategory) query.uniqueResult();
    }

    @Override
    public boolean isExist(Integer categoryID, String word) {
        Query query = getCurrentSession().createQuery("from WordListCategory w where w.category.id = :categoryID and w.wordList.id = :wordListID");
        query.setParameter("categoryID", categoryID);
        query.setParameter("wordListID", wordListDao.getID(word).getId());

        System.out.println(query.list().size() + " the size of the list");

        if(query.list().size() > 0){
            return true;
        }else{
            return false;
        }
    }
}
