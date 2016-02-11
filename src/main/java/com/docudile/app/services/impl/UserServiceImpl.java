package com.docudile.app.services.impl;

import com.docudile.app.data.dao.UserDao;
import com.docudile.app.data.dto.UserRegistrationDto;
import com.docudile.app.data.dto.UserShowDto;
import com.docudile.app.data.dto.UserUpdateDto;
import com.docudile.app.data.entities.User;
import com.docudile.app.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by franc on 2/8/2016.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public UserShowDto show(Integer id) {
        User user = userDao.show(id);
        return convert(user);
    }

    public UserShowDto show(String username) {
        User user = userDao.show(username);
        return convert(user);
    }

    public boolean update(Integer id, UserUpdateDto userUpdateDto) {
        User user = userDao.show(id);
        user.setUsername(userUpdateDto.getUsername());
        if (StringUtils.isNotEmpty(userUpdateDto.getOldPassword())) {
            if (user.getPassword().equals(userUpdateDto.getOldPassword()) && StringUtils.isNotEmpty(userUpdateDto.getNewPassword())) {
                user.setPassword(userUpdateDto.getNewPassword());
            } else {
                return false;
            }
        }
        user.setFirstname(userUpdateDto.getFirstname());
        user.setLastname(userUpdateDto.getLastname());
        return userDao.update(user);
    }

    public boolean delete(Integer id) {
        return userDao.delete(userDao.show(id));
    }

    public UserShowDto convert(User user) {
        UserShowDto dto = new UserShowDto();
        dto.setUsername(user.getUsername());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        return dto;
    }

}
