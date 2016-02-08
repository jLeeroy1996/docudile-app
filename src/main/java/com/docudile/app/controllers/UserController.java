package com.docudile.app.controllers;

import com.docudile.app.data.dto.UserDTO;
import com.docudile.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by PaulRyan on 2/8/2016.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String goRegister() {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
    public String submitRegistration(
            @ModelAttribute UserDTO user,
            HttpServletRequest request) {
        return userService.registerStart(user, request);
    }

    @RequestMapping(value = "/dropbox-auth-finish")
    public String dropboxFinish(HttpServletRequest request) {
        return userService.registerFinish(request);
    }

}
