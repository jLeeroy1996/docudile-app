package com.docudile.app.services;

import org.springframework.core.io.FileSystemResource;

import java.io.InputStream;

/**
 * Created by franc on 3/11/2016.
 */
public interface LocalStorageService {

    public boolean upload(String path, InputStream file);

    public FileSystemResource getFile(String path);

    public boolean deleteFile(String path);

}
