package com.docudile.app.services;

import com.docudile.app.data.dto.UserRegistrationDto;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by franc on 2/7/2016.
 */
public interface RegistrationService {

    public String registerStart(UserRegistrationDto user, HttpServletRequest request);

    public String registerFinish(HttpServletRequest request);

}
