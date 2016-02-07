package com.docudile.app.data.dao.impl;

import com.docudile.app.data.dao.GenericDao;
import com.google.common.base.Preconditions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by franc on 2/7/2016.
 */
@Transactional
public abstract class GenericDaoImpl<T> implements GenericDao<T> {

    private Class<T> thisClass;

    @Autowired
    protected SessionFactory sessionFactory;

    public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        thisClass = (Class) pt.getActualTypeArguments()[0];
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public boolean create(T data) {
        try {
            getCurrentSession().save(Preconditions.checkNotNull(data));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public T show(int id) {
        return getCurrentSession().get(thisClass, id);
    }

    public boolean update(T data) {
        try {
            getCurrentSession().merge(Preconditions.checkNotNull(data));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean delete(T data) {
        try {
            getCurrentSession().delete(data);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
