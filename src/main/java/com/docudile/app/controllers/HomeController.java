package com.docudile.app.controllers;

import com.docudile.app.data.dao.FolderDao;
import com.docudile.app.data.dao.UserDao;
import com.docudile.app.data.dto.FileShowDto;
import com.docudile.app.data.dto.FolderShowDto;
import com.docudile.app.data.dto.UploadResponseDto;
import com.docudile.app.services.FileSystemService;
import com.docudile.app.services.HomeService;
import com.docudile.app.services.TesseractService;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

/**
 * Created by franc on 2/6/2016.
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    UserDao userDao;

    @Autowired
    HomeService homeService;

    @Autowired
    FileSystemService fileSystemService;

    @RequestMapping()
    public String goHome(Principal principal) {
        return "home";
    }

    @RequestMapping(value = "/upload-documents", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<UploadResponseDto> uploadDoc(@RequestParam("document") MultipartFile document, Principal principal) {
        return homeService.uploadDoc(document, principal.getName());
    }

    @RequestMapping(value = "/tree")
    public @ResponseBody

    @RequestMapping(value = "/folder/{id}")
    public @ResponseBody FolderShowDto getFiles(@PathVariable("id") Integer folderId, Principal principal) {
        FolderShowDto folder = fileSystemService.getFolder(folderId, userDao.show(principal.getName()).getId());
        return folder;
    }

}
