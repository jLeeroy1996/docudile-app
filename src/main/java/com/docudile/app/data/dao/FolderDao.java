package com.docudile.app.data.dao;

import com.docudile.app.data.entities.Folder;

/**
 * Created by franc on 2/10/2016.
 */
public interface FolderDao extends GenericDao<Folder> {

    public Folder show(String name);

}
