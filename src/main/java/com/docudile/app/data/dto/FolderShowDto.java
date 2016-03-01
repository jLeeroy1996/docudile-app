package com.docudile.app.data.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by franc on 2/11/2016.
 */
public class FolderShowDto implements Serializable {

    private Integer id;
    private String name;
    private FolderShowDto parentFolder;
    private List<FolderShowDto> childFolders;
    private List<FileShowDto> files;
    private UserShowDto user;
    private String dateModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FolderShowDto getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(FolderShowDto parentFolder) {
        this.parentFolder = parentFolder;
    }

    public List<FolderShowDto> getChildFolders() {
        return childFolders;
    }

    public void setChildFolders(List<FolderShowDto> childFolders) {
        this.childFolders = childFolders;
    }

    public List<FileShowDto> getFiles() {
        return files;
    }

    public void setFiles(List<FileShowDto> files) {
        this.files = files;
    }

    public UserShowDto getUser() {
        return user;
    }

    public void setUser(UserShowDto user) {
        this.user = user;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

}
