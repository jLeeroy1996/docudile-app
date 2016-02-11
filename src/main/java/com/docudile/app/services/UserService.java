package com.docudile.app.services;

import com.docudile.app.data.dto.UserRegistrationDto;
import com.docudile.app.data.dto.UserShowDto;
import com.docudile.app.data.dto.UserUpdateDto;
import com.docudile.app.data.entities.User;

/**
 * Created by franc on 2/8/2016.
 */
public interface UserService {

    public UserShowDto show(Integer id);

    public UserShowDto show(String username);

    public boolean update(Integer id, UserUpdateDto userUpdateDto);

    public boolean delete(Integer id);

    public UserShowDto convert(User user);

}
