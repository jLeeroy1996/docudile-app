package com.docudile.app.data.dao;

import com.docudile.app.data.entities.Folder;

import java.util.List;
import java.util.Set;

/**
 * Created by franc on 2/10/2016.
 */
public interface FolderDao extends GenericDao<Folder> {

    public Folder show(String name);

    public Folder show(Integer id);

    public Folder show(String name, Integer parentId);

    public List<Folder> showAllByName(String name, Integer userId);

    public List<Folder> root(Integer userId);
}
