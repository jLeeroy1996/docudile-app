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

    @Override
    public Folder show(Integer id) {
        Query query = getCurrentSession().createQuery("from Folder f where f.id = :id");
        query.setParameter("id", id);
        return (Folder) query.uniqueResult();
    }

    @Override
    public Folder show(String name, Integer parentId) {
        Query query = getCurrentSession().createQuery("from Folder f where f.name = :name and f.parentFolder.id = :parentId");
        query.setParameter("name", name);
        query.setParameter("parentId", parentId);
        return (Folder) query.uniqueResult();
    }

    @Override
    public List<Folder> showAllByName(String name, Integer userId) {
        Query query = getCurrentSession().createQuery("from Folder f where f.name = :name and f.user.id = :userId");
        query.setParameter("name", name);
        query.setParameter("userId", userId);
        return query.list();
    }

    public List<Folder> root(Integer userId) {
        Query query = getCurrentSession().createQuery("from Folder f where f.parentFolder = null and f.user.id = :userId");
        query.setParameter("userId", userId);
        return query.list();
    }

}
