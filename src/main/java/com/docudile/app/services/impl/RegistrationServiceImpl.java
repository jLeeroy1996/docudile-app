package com.docudile.app.services.impl;

import com.docudile.app.data.dao.UserDao;
import com.docudile.app.data.dto.UserRegistrationDto;
import com.docudile.app.data.entities.User;
import com.docudile.app.services.DropboxService;
import com.docudile.app.services.RegistrationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by franc on 2/7/2016.
 */
@Service("registrationService")
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DropboxService dropboxService;

    public String registerStart(UserRegistrationDto user, HttpServletRequest request) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        HttpSession session = request.getSession();
        User userEntity = new User();
        userEntity.setLastname(user.getLastname());
        userEntity.setFirstname(user.getFirstname());
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userDao.create(userEntity)) {
            session.setAttribute("username", user.getUsername());
            return "redirect:" + dropboxService.linkDropbox(request);
        }
        return "redirect:/register?error=true";
    }

    public String registerFinish(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = session.getAttribute("username").toString();
        session.removeAttribute("username");
        String token = dropboxService.finishAuth(request);
        if (StringUtils.isNoneEmpty(username) && StringUtils.isNoneEmpty(token)) {
            User user = userDao.show(username);
            user.setDropboxAccessToken(token);
            if (userDao.update(user)) {
                return "redirect:/login";
            }
        }
        return "redirect:/register?error=true";
    }

}
