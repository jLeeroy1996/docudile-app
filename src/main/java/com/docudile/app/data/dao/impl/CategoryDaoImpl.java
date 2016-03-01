package com.docudile.app.data.dao.impl;

import com.docudile.app.data.dao.CategoryDao;
import com.docudile.app.data.entities.Category;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by cicct on 2/15/2016.
 */
@Repository("categoryDao")
@Transactional
public class CategoryDaoImpl extends GenericDaoImpl<Category> implements CategoryDao {

    public List<Category> getCategories(Integer userId) {
        Query query = getCurrentSession().createQuery("from Category f where f.user.id = :userId");
        query.setParameter("userId", userId);
        return query.list();
    }

    public Category getCategory(Integer categoryID) {
        Query query = getCurrentSession().createQuery("from Category f where f.category.id = :categoryID");
        query.setParameter("categoryID", categoryID);
        return (Category) query.uniqueResult();
    }
}
