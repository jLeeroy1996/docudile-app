package com.docudile.app.data.dao;

import java.util.List;

/**
 * Created by franc on 2/7/2016.
 */
public interface GenericDao<T> {

    public boolean create(T data);

    public T show(int id);

    public boolean update(T data);

    public boolean delete(T data);

}
