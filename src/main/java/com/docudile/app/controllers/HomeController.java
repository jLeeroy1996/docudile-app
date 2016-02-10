package com.docudile.app.controllers;

import com.docudile.app.data.dto.UploadResponseDto;
import com.docudile.app.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * Created by franc on 2/6/2016.
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    HomeService homeService;

    @RequestMapping("/")
    public String goHome() {
        return "home";
    }

    @RequestMapping(value = "/upload-documents", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<UploadResponseDto> uploadDoc(@RequestParam("document") MultipartFile document, Principal principal) {
        return homeService.uploadDoc(document, principal.getName());
    }

    @RequestMapping(value = "/new-type", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<UploadResponseDto> submitNewType(
            @RequestParam("sampleDocuments") MultipartFile[] documents,
            @RequestParam("typeName") String typeName,
            Principal principal) {

    }

}
