package com.docudile.app.services.impl;

import com.docudile.app.data.dao.FolderDao;
import com.docudile.app.data.dao.UserDao;
import com.docudile.app.data.dto.FolderShowDto;
import com.docudile.app.data.dto.UserRegistrationDto;
import com.docudile.app.data.entities.Folder;
import com.docudile.app.data.entities.User;
import com.docudile.app.services.DropboxService;
import com.docudile.app.services.FileSystemService;
import com.docudile.app.services.RegistrationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by franc on 2/7/2016.
 */
@Service("registrationService")
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private FolderDao folderDao;

    @Autowired
    private FileSystemService fileSystemService;

    @Autowired
    private DropboxService dropboxService;

    public String registerStart(UserRegistrationDto user, HttpServletRequest request) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        HttpSession session = request.getSession();
        User userEntity = new User();
        userEntity.setLastname(user.getLastname());
        userEntity.setFirstname(user.getFirstname());
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setOffice(user.getOffice());
        if (userDao.create(userEntity)) {
            session.setAttribute("username", user.getUsername());
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:" + dropboxService.linkDropbox(request);
        }
        return "redirect:/register?error=true";
    }

    public String registerFinish(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = session.getAttribute("username").toString();
        session.removeAttribute("username");
        String token = dropboxService.finishAuth(request);
        if (StringUtils.isNoneEmpty(username) && StringUtils.isNoneEmpty(token)) {
            User user = userDao.show(username);
            user.setDropboxAccessToken(token);
            if (userDao.update(user)) {
                return "redirect:/setup/content";
            }
        }
        return "redirect:/register?error=true";
    }

    public String createFolders(User user, HttpServletRequest request) {
        int start = Integer.parseInt(request.getParameter("startYear"));
        int end = Integer.parseInt(request.getParameter("endYear"));
        for(int x = start; x <= end; x++) {
            Folder f = new Folder();
            f.setName(x+"");
            f.setParentFolder(null);
            f.setUser(user);
            folderDao.create(f);
            dropboxService.createFolder("/"+x, userDao.show(user.getId()).getDropboxAccessToken());
            createSubFolders("Memo", f.getId(), user, "/"+x+"/");
            createSubFolders("Letter", f.getId(), user, "/"+x+"/");
            Folder un = new Folder();
            un.setName("uncategorized");
            un.setParentFolder(folderDao.show(f.getId()));
            un.setUser(user);
            folderDao.create(un);
            dropboxService.createFolder("/"+x+"/uncategorized", userDao.show(user.getId()).getDropboxAccessToken());
        }
        Folder f = new Folder();
        f.setName("uncategorized");
        f.setParentFolder(null);
        f.setUser(user);
        folderDao.create(f);
        dropboxService.createFolder("/uncategorized", userDao.show(user.getId()).getDropboxAccessToken());
        return "redirect:/setup/data";
    }

    public void createSubFolders(String name, Integer id, User user, String path) {
        Folder f = new Folder();
        Folder f2 = new Folder();
        Folder f3 = new Folder();
        Folder f4 = new Folder();
        f.setName(name);
        f.setParentFolder(folderDao.show(id));
        f.setUser(user);
        folderDao.create(f);
        dropboxService.createFolder(path+name, userDao.show(user.getId()).getDropboxAccessToken());
        f2.setName("from");
        f2.setParentFolder(folderDao.show(f.getId()));
        f2.setUser(user);
        folderDao.create(f2);
        dropboxService.createFolder(path+name+"/from", userDao.show(user.getId()).getDropboxAccessToken());
        f3.setName("to");
        f3.setParentFolder(folderDao.show(f.getId()));
        f3.setUser(user);
        folderDao.create(f3);
        dropboxService.createFolder(path+name+"/to", userDao.show(user.getId()).getDropboxAccessToken());
        f4.setName("uncategorized");
        f4.setParentFolder(folderDao.show(f.getId()));
        f4.setUser(user);
        folderDao.create(f4);
        dropboxService.createFolder(path+name+"/uncategorized", userDao.show(user.getId()).getDropboxAccessToken());
    }

}
