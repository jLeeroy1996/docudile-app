package com.docudile.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by franc on 2/6/2016.
 */
@Controller
public class HomeController {

    @RequestMapping("/home")
    public String goHome() {
        return "home";
    }

}
