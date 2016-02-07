package com.docudile.app.services.impl;

import com.docudile.app.data.dto.UserDTO;
import com.docudile.app.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by franc on 2/7/2016.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    public ModelAndView add(UserDTO user) {
        ModelAndView mav = new ModelAndView("");
        return mav;
    }

    public ModelAndView show(Integer id) {
        return null;
    }

    public ModelAndView update(UserDTO user, Integer id) {
        return null;
    }

    public ModelAndView delete(Integer id) {
        return null;
    }

}
