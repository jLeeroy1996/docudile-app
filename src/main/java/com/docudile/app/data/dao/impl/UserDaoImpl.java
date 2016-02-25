package com.docudile.app.data.dao.impl;

import com.docudile.app.data.dao.UserDao;
import com.docudile.app.data.entities.User;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by franc on 2/7/2016.
 */
@Repository("userDao")
@Transactional
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

    public User show(String username) {
        Query query = getCurrentSession().createQuery("from User u where u.username = :username");
        query.setParameter("username", username);
        return (User) query.uniqueResult();
    }

    public User getUserDetails(Integer userID) {
        Query query = getCurrentSession().createQuery("from User u where u.id = :userID");
        query.setParameter("userID", userID);
        return (User) query.uniqueResult();
    }

}
