package com.docudile.app.services.impl;

import com.docudile.app.data.dao.UserDao;
import com.docudile.app.data.dto.AuthDTO;
import com.docudile.app.data.entities.User;
import com.docudile.app.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by franc on 2/8/2016.
 */
@Service("authService")
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDao userDao;

    public String authenticate(AuthDTO authDTO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = userDao.show(authDTO.getUsername());
        if (user.getPassword().equals(authDTO.getPassword())) {
            session.setAttribute("curr_user", user.getId());
            return "redirect:/home";
        }
        return "redirect:/login?error=true";
    }

}
