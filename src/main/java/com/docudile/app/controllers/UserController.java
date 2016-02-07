package com.docudile.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by PaulRyan on 2/8/2016.
 */
@Controller
public class UserController {

    @RequestMapping("/register")
    public String goRegister() {
        return "register";
    }

    @RequestMapping("/login")
    public String goLogin() {
        return "login";
    }
}
