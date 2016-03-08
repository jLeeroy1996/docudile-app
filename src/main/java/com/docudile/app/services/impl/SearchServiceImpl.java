package com.docudile.app.services.impl;

import com.docudile.app.data.dao.FileDao;
import com.docudile.app.data.dao.WordListDao;
import com.docudile.app.data.dao.WordListDocumentDao;
import com.docudile.app.data.entities.File;
import com.docudile.app.data.entities.WordList;
import com.docudile.app.data.entities.WordListDocument;
import com.docudile.app.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by cicct on 2/25/2016.
 */
@Service("searchService")
@Transactional
public class SearchServiceImpl implements SearchService {

    @Autowired
    private FileDao fileDao;

    @Autowired
    private WordListDao wordListDao;

    @Autowired
    private WordListDocumentDao wordListDocumentDao;

    public List<WordListDocument> search(Integer userId, String searchString) {
        boolean isExist = false;
        List<WordListDocument> documentID = new ArrayList<>();
        List<File> files = fileDao.getSpecificFiles(userId);
        WordList wordList;
        StringTokenizer st = new StringTokenizer(searchString);
        while (st.hasMoreTokens()) {
            String word = st.nextToken();
            wordList = wordListDao.getID(word);
            List<WordListDocument> documents = wordListDocumentDao.getID(files, wordList.getId());
            documentID.add(documents.get(0));
            System.out.println(documents.get(0).getId() + " paulryanluceroboob");
        }
        return documentID;
    }
}
