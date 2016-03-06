package com.docudile.app.controllers;

import com.docudile.app.data.dao.UserDao;
import com.docudile.app.data.dto.UserRegistrationDto;
import com.docudile.app.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by PaulRyan on 2/8/2016.
 */
@Controller
public class UserController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    UserDao userDao;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String goRegister() {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
    public String submitRegistration(
            @ModelAttribute UserRegistrationDto user,
            HttpServletRequest request) {
        return registrationService.registerStart(user, request);
    }

    @RequestMapping(value = "/dropbox-auth-finish")
    public String dropboxFinish(HttpServletRequest request) {
        return registrationService.registerFinish(request);
    }

}
