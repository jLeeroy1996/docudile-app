package com.docudile.app.services;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by franc on 2/7/2016.
 */
public interface DropboxService {

    public String linkDropbox(HttpServletRequest request);

    public String finishAuth(HttpServletRequest request);

}
