package com.docudile.app.data.dao;

import com.docudile.app.data.entities.Category;

import java.util.List;

/**
 * Created by cicct on 2/15/2016.
 */

public interface CategoryDao extends GenericDao<Category> {

    public List<Category> getCategories(Integer userId);

    public Category getCategory(Integer userID);

    public Category getCategory(String categoryName,Integer userId);
}