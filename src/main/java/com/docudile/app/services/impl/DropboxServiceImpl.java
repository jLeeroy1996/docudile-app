package com.docudile.app.services.impl;

import com.docudile.app.services.DropboxService;
import com.dropbox.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

/**
 * Created by franc on 2/7/2016.
 */
@Service("dropboxService")
@Transactional
@PropertySource({"classpath:/dropbox.properties"})
public class DropboxServiceImpl implements DropboxService {

    @Autowired
    private Environment environment;

    public String linkDropbox(HttpServletRequest request) {
        DbxAppInfo appInfo = new DbxAppInfo(environment.getProperty("dropbox.appkey"), environment.getProperty("dropbox.appsecret"));
        DbxRequestConfig config = new DbxRequestConfig("Docudi.le/1.0", Locale.getDefault().toString());
        HttpSession session = request.getSession();
        String sessionKey = "dropbox-auth-csrf-token";
        DbxSessionStore sessionStore = new DbxStandardSessionStore(session, sessionKey);
        String redirectUri = "http://localhost:8080/dropbox-auth-finish";
        DbxWebAuth webAuth = new DbxWebAuth(config, appInfo, redirectUri, sessionStore);
        return webAuth.start();
    }

    public String finishAuth(HttpServletRequest request) {
        DbxAppInfo appInfo = new DbxAppInfo(environment.getProperty("dropbox.appkey"), environment.getProperty("dropbox.appsecret"));
        DbxRequestConfig config = new DbxRequestConfig("Docudi.le/1.0", Locale.getDefault().toString());
        HttpSession session = request.getSession();
        String sessionKey = "dropbox-auth-csrf-token";
        DbxSessionStore sessionStore = new DbxStandardSessionStore(session, sessionKey);
        String redirectUri = "http://localhost:8080/dropbox-auth-finish";
        DbxWebAuth webAuth = new DbxWebAuth(config, appInfo, redirectUri, sessionStore);
        try {
            return webAuth.finish(request.getParameterMap()).accessToken;
        } catch (Exception ex) {
            return null;
        }
    }

}
