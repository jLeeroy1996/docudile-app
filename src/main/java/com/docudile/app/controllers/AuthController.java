package com.docudile.app.controllers;

import com.docudile.app.data.dto.AuthDTO;
import com.docudile.app.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by franc on 2/8/2016.
 */
@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String goLogin() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
    public String submitLogin(
            @ModelAttribute AuthDTO authDTO,
            HttpServletRequest request) {
        return authService.authenticate(authDTO, request);
    }

}
