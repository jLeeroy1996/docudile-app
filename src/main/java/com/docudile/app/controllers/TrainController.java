package com.docudile.app.controllers;

import com.docudile.app.data.dto.GeneralMessageResponseDto;
import com.docudile.app.data.dto.ModTagRequestDto;
import com.docudile.app.services.DocumentService;
import com.docudile.app.services.impl.DocumentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

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

    @CrossOrigin(origins = "http://localhost:9000")
    @RequestMapping(value = "/trainTag", method = RequestMethod.POST, headers ="content-type=application/json")
    public @ResponseBody GeneralMessageResponseDto trainTag(@RequestBody List<ModTagRequestDto> request, Principal principal) {
        return documentService.trainTag(request, principal.getName());
    }

    @RequestMapping(value = "/deleteTag", method = RequestMethod.POST)
    public @ResponseBody GeneralMessageResponseDto deleteTag(@RequestParam("tagName") String tagName, Principal principal) {
        return documentService.deleteTag(tagName, principal.getName());
    }

    @RequestMapping(value = "/trainClassifier", method = RequestMethod.POST)
    public @ResponseBody GeneralMessageResponseDto trainClassifier(@RequestPart("name") String name, @RequestPart("file") MultipartFile file, Principal principal) {
        return documentService.trainClassifier(name, file, principal.getName());
    }

    @RequestMapping(value = "/trainCategory", method = RequestMethod.POST)
    public @ResponseBody GeneralMessageResponseDto trainCategory(@RequestPart("name") String name, @RequestPart("content_new") MultipartFile file, @RequestPart("categoryName") String categoryName, Principal principal) throws IOException {
        return documentService.contentTrain(name,file,categoryName);
    }
}
