package com.docudile.app.controllers;

import com.docudile.app.data.dto.GeneralMessageResponseDto;
import com.docudile.app.data.dto.ModTagRequestDto;
import com.docudile.app.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

/**
 * Created by PaulRyan on 3/2/2016.
 */
@Controller
public class TrainController {
    @Autowired
    DocumentService documentService;

    @RequestMapping("/training/content")
    public String goRetrain() {
        return "training-content";
    }

    @RequestMapping(value = "/training/trainTag", method = RequestMethod.POST, headers ="content-type=application/json")
    public @ResponseBody GeneralMessageResponseDto trainTag(@RequestBody ModTagRequestDto request, Principal principal) {
        return documentService.trainTag(request, principal.getName());
    }

    @RequestMapping(value = "/deleteTag", method = RequestMethod.POST)
    public @ResponseBody GeneralMessageResponseDto deleteTag(@RequestParam("tagName") String tagName, Principal principal) {
        return documentService.deleteTag(tagName, principal.getName());
    }

    @RequestMapping(value = "/training/trainClassifier", method = RequestMethod.POST)
    public @ResponseBody GeneralMessageResponseDto trainClassifier(@RequestPart("name") String name, @RequestPart("file") MultipartFile file, Principal principal) {
        return documentService.trainClassifier(name, file, principal.getName());
    }

    @RequestMapping(value = "/training/category/new", method = RequestMethod.POST)
    public @ResponseBody GeneralMessageResponseDto trainCategory(@RequestParam("category_name") String name,
                                                                 @RequestPart("file") MultipartFile file,
                                                                 Principal principal) throws IOException {
        System.out.println("Name: " + name + " File: " + file.getOriginalFilename());
        return documentService.contentTrain(principal.getName(), file, name);
    }
}
