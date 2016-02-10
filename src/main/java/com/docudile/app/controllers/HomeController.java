package com.docudile.app.controllers;

import com.docudile.app.data.dto.UploadResponseDto;
import com.docudile.app.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by franc on 2/6/2016.
 */
@Controller
public class HomeController {

    @Autowired
    DocumentService documentService;

    @RequestMapping("/home")
    public String goHome() {
        return "home";
    }

    @RequestMapping(value = "/upload-documents", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<UploadResponseDto> submit(@RequestParam("document") MultipartFile document) {
        return documentService.upload(document);
    }

}
