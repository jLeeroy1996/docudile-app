package com.docudile.app.controllers;

import com.docudile.app.data.dao.UserDao;
import com.docudile.app.data.dto.FolderShowDto;
import com.docudile.app.data.dto.GeneralMessageResponseDto;
import com.docudile.app.services.DocumentService;
import com.docudile.app.services.FileSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    DocumentService documentService;

    @RequestMapping()
    public String goHome() {
        return "home";
    }

    @RequestMapping(value = "/upload-documents", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<GeneralMessageResponseDto> uploadDoc(@RequestParam("document") MultipartFile document, Principal principal) {
        return ResponseEntity.ok(documentService.classifyThenUpload(document, principal.getName()));
    }

    @RequestMapping(value = "/folder")
    public @ResponseBody List<FolderShowDto> showRoot(Principal principal) {
        return documentService.showRoot(principal.getName());
    }

    @RequestMapping(value = "/folder/{id}")
    public @ResponseBody FolderShowDto showFolder(@PathVariable("id") Integer folderId, Principal principal) {
        return documentService.showFolder(folderId, principal.getName());
    }

}
