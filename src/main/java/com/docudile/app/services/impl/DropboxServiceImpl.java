package com.docudile.app.services.impl;

import com.docudile.app.services.DropboxService;
import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public String linkDropbox() {
        DbxAppInfo appInfo = new DbxAppInfo(environment.getProperty("dropbox.appkey"), environment.getProperty("dropbox.appsecret"));
        DbxRequestConfig config = new DbxRequestConfig("Docudi.le/1.0", Locale.getDefault().toString());
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);

        String authUrl = webAuth.start();
        try {
            String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
            DbxAuthFinish authFinish = webAuth.finish(code);
            return authFinish.accessToken;
        } catch (Exception ex) {
            return null;
        }
    }

}
