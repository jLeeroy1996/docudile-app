package com.docudile.app.services;

import com.docudile.app.data.dto.AuthDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by franc on 2/8/2016.
 */
public interface AuthService {

    public String authenticate(AuthDTO authDTO, HttpServletRequest request);

}
