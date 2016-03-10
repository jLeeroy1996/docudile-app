package com.docudile.app.controllers;

import com.docudile.app.data.dao.UserDao;
import com.docudile.app.data.dto.GeneralMessageResponseDto;
import com.docudile.app.data.dto.ModTagRequestDto;
import com.docudile.app.data.dto.TrainingCatNewDto;
import com.docudile.app.services.DocumentService;
import com.docudile.app.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
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
    private UserDao userDao;

    @RequestMapping("/setup/content")
    public ModelAndView goContent(Principal principal) {
        ModelAndView mav = new ModelAndView("training-content");
        mav.addObject("user", userDao.show(principal.getName()));
        return mav;
    }

    @RequestMapping(value = "/training/category/new", method = RequestMethod.POST)
    public @ResponseBody GeneralMessageResponseDto trainCategory(@RequestParam("category_name") String name,
                                                                 @ModelAttribute("file") TrainingCatNewDto file,
                                                                 Principal principal) throws IOException {
        System.out.println("Size: " + file.getFile().size() + ", Category Name: " + name);
        MultipartFile f[] = new MultipartFile[file.getFile().size()];
        for(int x = 0;x<file.getFile().size();x++){
            f[x] = file.getFile().get(x);
        }
        return documentService.contentTrain(principal.getName(), f, name);
    }

}
