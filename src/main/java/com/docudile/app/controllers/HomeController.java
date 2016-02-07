package com.docudile.app.controllers;

import com.docudile.app.services.DropboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by franc on 2/6/2016.
 */
@Controller
public class HomeController {

    @Autowired
    DropboxService dropboxService;

    @RequestMapping("/home")
    public String goHome() {
        return "home";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ModelAndView submit(@RequestParam("file")MultipartFile file) {
        return new ModelAndView("home").addObject("filename", file.getOriginalFilename());
    }

    @RequestMapping(value = "/dropbox", method = RequestMethod.GET)
    public String dropbox(HttpServletRequest request) {
        return dropboxService.linkDropbox(request);
    }



}
