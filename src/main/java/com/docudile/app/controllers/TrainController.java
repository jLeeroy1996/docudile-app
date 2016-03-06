package com.docudile.app.controllers;

import com.docudile.app.data.dao.UserDao;
import com.docudile.app.data.dto.GeneralMessageResponseDto;
import com.docudile.app.data.dto.ModTagRequestDto;
import com.docudile.app.services.DocumentService;
import com.docudile.app.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserDao userDao;

    @RequestMapping("/training/content")
    public String goRetrain() {
        return "training-content";
    }

    @RequestMapping(value = "/setup/year", method = RequestMethod.POST)
    public String doSetupYear(Principal principal, HttpServletRequest request) {
        return registrationService.createFolders(userDao.show(principal.getName()), request);
    }

    @RequestMapping("/setup/data")
    public ModelAndView goSetupData(Principal principal) {
        ModelAndView mv = new ModelAndView("setup-data");
        mv.addObject("user", userDao.show(principal.getName()));
        return mv;
    }

    @RequestMapping("/setup/classifier")
    public String goPreTrain() {
        return "setup-classifier";
    }

    @RequestMapping(value = "/training/tagger", method = RequestMethod.POST)
    public @ResponseBody GeneralMessageResponseDto trainTag(@RequestBody ModTagRequestDto request, Principal principal) {
        return documentService.trainTag((ModTagRequestDto) request, principal.getName());
    }

    @RequestMapping(value = "/deleteTag", method = RequestMethod.POST)
    public @ResponseBody GeneralMessageResponseDto deleteTag(@RequestParam("tagName") String tagName, Principal principal) {
        return documentService.deleteTag(tagName, principal.getName());
    }

    @RequestMapping(value = "/training/classifier", method = RequestMethod.POST)
    public @ResponseBody GeneralMessageResponseDto trainClassifier(@RequestParam("type_name") String typeName, @RequestPart("file") MultipartFile file, Principal principal) {
        return documentService.trainClassifier(typeName, file, principal.getName());
    }
    @RequestMapping(value = "/training/category/new", method = RequestMethod.POST)
    public @ResponseBody GeneralMessageResponseDto trainCategory(@RequestParam("category_name") String name,
                                                                 @RequestPart("file") MultipartFile file,
                                                                 Principal principal) throws IOException {
        return documentService.contentTrain(principal.getName(), file, name);
    }

}
