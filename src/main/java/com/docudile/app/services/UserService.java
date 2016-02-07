package com.docudile.app.services;

import com.docudile.app.data.dto.UserDTO;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by franc on 2/7/2016.
 */
public interface UserService {

    public String registerStart(UserDTO user, HttpServletRequest request);

    public String registerFinish(HttpServletRequest request);

    public ModelAndView show(Integer id);

    public ModelAndView update(UserDTO user, Integer id);

    public ModelAndView delete(Integer id);

}
