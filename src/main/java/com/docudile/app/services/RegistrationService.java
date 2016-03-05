package com.docudile.app.services;

import com.docudile.app.data.dto.UserRegistrationDto;
import com.docudile.app.data.entities.User;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * Created by franc on 2/7/2016.
 */
public interface RegistrationService {

    public String registerStart(UserRegistrationDto user, HttpServletRequest request);

    public String registerFinish(HttpServletRequest request);

    public String createFolders(User user, HttpServletRequest request);

    public void createSubFolders(String name, Integer id, User user, String path);

}
