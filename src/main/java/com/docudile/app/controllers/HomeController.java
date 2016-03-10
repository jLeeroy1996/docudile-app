package com.docudile.app.controllers;

import com.docudile.app.data.dao.UserDao;
import com.docudile.app.data.dto.FileShowDto;
import com.docudile.app.data.dto.FolderShowDto;
import com.docudile.app.data.dto.GeneralMessageResponseDto;
import com.docudile.app.services.DocumentService;
import com.docudile.app.services.FileSystemService;
import com.docudile.app.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
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

    @Autowired
    SearchService searchService;

    @Autowired
    FileSystemService fileSystemService;

    @RequestMapping()
    public String goHome() {
        return "home";
    }

    @RequestMapping(value = "/upload-documents", method = RequestMethod.POST)
    public @ResponseBody GeneralMessageResponseDto uploadDoc(@RequestParam("document") MultipartFile document, Principal principal) {
        return documentService.classifyThenUpload(document, principal.getName());
    }

    @RequestMapping(value = "/file/download/{id}")
    public FileSystemResource downloadFile(@PathVariable("id") Integer id, Principal principal) {
        return documentService.showFile(id, principal.getName());
    }

    @RequestMapping(value = "/folder")
    public @ResponseBody List<FolderShowDto> showRoot(Principal principal) {
        return documentService.showRoot(principal.getName());
    }

    @RequestMapping(value = "/folder/{id}")
    public @ResponseBody FolderShowDto showFolder(@PathVariable("id") Integer folderId, Principal principal) {
        return documentService.showFolder(folderId, principal.getName());
    }

    @RequestMapping(value = "/search/{string}")
    public @ResponseBody List<FileShowDto> searchFile(@PathVariable("string") String queryString, Principal principal) {
        return fileSystemService.getFilesFromId(searchService.search(userDao.show(principal.getName()).getId(), queryString), userDao.show(principal.getName()).getId());
    }

}
