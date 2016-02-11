package com.docudile.app.data.dto;

import java.io.Serializable;

/**
 * Created by franc on 2/11/2016.
 */
public class FileShowDto implements Serializable {

    private Integer id;
    private String filename;
    private String path;
    private String dateUploaded;
    private UserShowDto user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(String dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public UserShowDto getUser() {
        return user;
    }

    public void setUser(UserShowDto user) {
        this.user = user;
    }

}
