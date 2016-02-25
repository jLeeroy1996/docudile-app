package com.docudile.app.data.dao;

import com.docudile.app.data.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by franc on 2/7/2016.
 */
@Repository("userDao")
@Transactional
public interface UserDao extends GenericDao<User> {

    public User show(String username);

    public User getUserDetails(Integer userID);

}
