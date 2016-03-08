package com.docudile.app.data.dao;

import com.docudile.app.data.entities.File;

import java.util.List;

/**
 * Created by franc on 2/10/2016.
 */
public interface FileDao extends GenericDao<File> {

    public File numberOfFiles(Integer categoryID);

    public List<File> getSpecificFiles(Integer userID);

    public File getFileID(String filename, Integer userID);
}
