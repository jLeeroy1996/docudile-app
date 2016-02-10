package com.docudile.app.data.dao.impl;

import com.docudile.app.data.dao.FileDao;
import com.docudile.app.data.entities.File;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by franc on 2/10/2016.
 */
@Repository("fileDao")
@Transactional
public class FileDaoImpl extends GenericDaoImpl<File> implements FileDao {
}
