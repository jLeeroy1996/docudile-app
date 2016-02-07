package com.docudile.app.services;

import com.docudile.app.data.dto.UserDTO;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by franc on 2/7/2016.
 */
public interface UserService {

    public ModelAndView add(UserDTO user);

    public ModelAndView show(Integer id);

    public ModelAndView update(UserDTO user, Integer id);

    public ModelAndView delete(Integer id);

}
