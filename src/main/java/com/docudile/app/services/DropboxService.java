package com.docudile.app.services;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

/**
 * Created by franc on 2/7/2016.
 */
public interface DropboxService {

    public String linkDropbox(HttpServletRequest request);

    public String finishAuth(HttpServletRequest request);

    public boolean createFolder(String path, String accessToken);

    public boolean uploadFile(String path, InputStream file, String accessToken);

    public String getFile(String path, String accessToken);

    public boolean deleteFile(String path, String accessToken);

}
