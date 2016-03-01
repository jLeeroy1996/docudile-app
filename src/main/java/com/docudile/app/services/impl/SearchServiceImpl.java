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

import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

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

    public Set<WordListDocument> search(Integer userId, String searchString) {
        Set<WordListDocument> documentID = new TreeSet();
        List<File> files = fileDao.getSpecificFiles(userId);
        List<WordListDocument> documents = null;
        WordList wordList;
        StringTokenizer st = new StringTokenizer(searchString);
        while (st.hasMoreTokens()) {
            String word = st.nextToken();
            wordList = wordListDao.getID(word);
            documents = wordListDocumentDao.getID(files, wordList.getId());
            documentID.addAll(documents);
        }
        return documentID;
    }
}
