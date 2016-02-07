package com.docudile.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by franc on 2/6/2016.
 */
@Controller
public class HomeController {

    @RequestMapping("/home")
    public String goHome() {
        return "home";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ModelAndView submit(@RequestParam("file")MultipartFile file) {
        return new ModelAndView("home").addObject("filename", file.getOriginalFilename());
    }

}
