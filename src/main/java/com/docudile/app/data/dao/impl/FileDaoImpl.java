package com.docudile.app.data.dao.impl;

import com.docudile.app.data.dao.FileDao;
import com.docudile.app.data.dao.FolderDao;
import com.docudile.app.data.entities.File;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by franc on 2/10/2016.
 */
@Repository("fileDao")
@Transactional
public class FileDaoImpl extends GenericDaoImpl<File> implements FileDao {

    @Autowired
    FolderDao folderDao;

    public Integer numberOfFiles(Integer categoryID) {
        Query query = getCurrentSession().createQuery("from File f where f.category.id = :categoryID");
        query.setParameter("categoryID", categoryID);
        return query.getMaxResults();
    }

    public List<File> getSpecificFiles(Integer userID) {
        Query query = getCurrentSession().createQuery("from File f where f.user.id = :userID");
        query.setParameter("userID", userID);
        return query.list();


    }



    public List<File> getTrainingFiles(Integer userID){
        Query query = getCurrentSession().createQuery("from File f where f.folder.id = :folderID");
        query.setParameter("folderID", folderDao.getFolderIDOfTraining(userID).getId());
        return query.list();
    }

}
