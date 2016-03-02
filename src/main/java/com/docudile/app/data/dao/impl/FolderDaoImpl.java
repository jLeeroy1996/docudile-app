package com.docudile.app.data.dao.impl;

import com.docudile.app.data.dao.FolderDao;
import com.docudile.app.data.entities.Folder;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

    public List<Folder> root(Integer userId) {
        Query query = getCurrentSession().createQuery("from Folder f where f.parentFolder = null and f.user.id = :userId");
        query.setParameter("userId", userId);
        return query.list();
    }

    @Override
    public Folder getFolderIDOfTraining(Integer userId) {
        Query query = getCurrentSession().createQuery("from Folder f where f.name = :contentTraining and f.user.id = :userId");
        query.setParameter("contentTraining","contentTraining");
        query.setParameter("userId",userId);
        return (Folder) query.uniqueResult();
    }

}
