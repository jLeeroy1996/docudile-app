package com.docudile.app.data.dao.impl;

import com.docudile.app.data.dao.FolderDao;
import com.docudile.app.data.entities.Folder;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by franc on 2/10/2016.
 */
@Repository("folderDao")
@Transactional
public class FolderDaoImpl extends GenericDaoImpl<Folder> implements FolderDao {

    public Folder show(String name) {
        Query query = getCurrentSession().createQuery("from Folder f where f.name = :name");
        query.setParameter("name", name);
        return (Folder) query.uniqueResult();
    }

}
