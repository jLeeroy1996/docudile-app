package com.docudile.app.data.dao.impl;

import com.docudile.app.data.dao.StructureTrainingDao;
import com.docudile.app.data.entities.StructureTraining;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by franc on 2/12/2016.
 */
@Repository("structureTraining")
@Transactional
public class StructureTrainingDaoImpl extends GenericDaoImpl<StructureTraining> implements StructureTrainingDao {
}
