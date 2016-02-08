package com.docudile.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by franc on 2/8/2016.
 */
@Controller
public class AuthController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView goLogin(
            @RequestParam(name = "error", required = false) boolean error) {
        ModelAndView mav = new ModelAndView("login");
        if (error) {
            mav.addObject("error", "There seems to be a problem.");
        }
        return mav;
    }

}
