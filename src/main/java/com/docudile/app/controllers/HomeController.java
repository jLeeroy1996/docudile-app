package com.docudile.app.controllers;

import com.docudile.app.data.dao.FolderDao;
import com.docudile.app.data.dao.UserDao;
import com.docudile.app.data.dto.UploadResponseDto;
import com.docudile.app.services.FileSystemService;
import com.docudile.app.services.HomeService;
import com.docudile.app.services.TesseractService;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * Created by franc on 2/6/2016.
 */
@Controller
public class HomeController {

    @Autowired
    UserDao userDao;

    @Autowired
    HomeService homeService;

    @Autowired
    FileSystemService fileSystemService;

    @Autowired
    TesseractService tesseractService;

    @RequestMapping("/home")
    public ModelAndView goHome(Principal principal) {
        ModelAndView mv = new ModelAndView("home");
        mv.addObject("nodes", fileSystemService.getRootFolders(userDao.show(principal.getName()).getId()));
        return mv;
    }

    @RequestMapping("/home/**")
    public ModelAndView navigateHomeFolders(Principal principal) {
        ModelAndView mv = new ModelAndView("home");
        mv.addObject("nodes", fileSystemService.getRootFolders(userDao.show(principal.getName()).getId()));
        return mv;
    }

    @RequestMapping(value = "/upload-documents", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<UploadResponseDto> uploadDoc(@RequestParam("document") MultipartFile document, Principal principal) {

        return homeService.uploadDoc(document, principal.getName());
    }

    @RequestMapping(value = "/get-files", method = RequestMethod.GET)
    public String getFiles(Principal principal) {
        return "get-files";
    }

//    @RequestMapping(value = "/new-type", method = RequestMethod.POST)
//    public @ResponseBody ResponseEntity<UploadResponseDto> submitNewType(
//            @RequestParam("sampleDocuments") MultipartFile[] documents,
//            @RequestParam("typeName") String typeName,
//            Principal principal) {
//
//    }

}
