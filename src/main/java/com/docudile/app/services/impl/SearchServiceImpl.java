package com.docudile.app.services.impl;

import com.docudile.app.data.dao.FileDao;
import com.docudile.app.data.dao.WordListDao;
import com.docudile.app.data.dao.DocumentIndexDao;
import com.docudile.app.data.entities.DocumentIndex;
import com.docudile.app.data.entities.File;
import com.docudile.app.data.entities.WordList;
import com.docudile.app.services.SearchService;
import com.docudile.app.services.StemmerService;
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
    private StemmerService stemmerService;

    @Autowired
    private DocumentIndexDao documentIndexDao;

    public List<DocumentIndex> search(Integer userId, String searchString) {
        boolean isExist = false;
        List<DocumentIndex> documentID = new ArrayList<>();
        List<File> files = fileDao.getSpecificFiles(userId);
        WordList wordList;
        StringTokenizer st = new StringTokenizer(searchString);
        while (st.hasMoreTokens()) {
            String word = st.nextToken();
            wordList = wordListDao.getID(word);
            List<DocumentIndex> documents = documentIndexDao.getID(files, wordList.getId());
            for(int x = 0; x < documents.size(); x++) {
                for(int y = 0; y < documentID.size(); y++) {
                    if(documents.get(x).getFile().getId() == documentID.get(y).getFile().getId()){
                        isExist = true;
                        break;
                    }
                }
                if(!isExist) {
                    if(documents.get(x).getCount() > 0)
                        documentID.add(documents.get(x));
                }
                isExist = false;
            }
//            System.out.println(documents.get(0).getId() + " paulryanluceroboob");
        }
        return documentID;
    }

    public void generateWordList(List<String> words, Integer userId){
        List<String> temp = new ArrayList<>();
        for(int x = 0;x<words.size();x++){
            StringTokenizer st = new StringTokenizer(words.get(x)," ~`!@#$%^&*()-=_+[]{};'\\:|,./<>?");
            while(st.hasMoreTokens()){
                String word = st.nextToken();
                if(word.length()>1) {
                    temp.add(st.nextToken());
                }
            }
        }
        words = preprocessWords(temp);


        List<String> wordListWords = wordListDao.getWords();

        boolean isExist = false;


        for(int x = 0;x<words.size();x++){
            for(int y=  0;y<wordListWords.size();y++){
                if(words.get(x).equalsIgnoreCase(wordListWords.get(y))){
                    isExist = true;
                    break;
                }
            }
            if(!isExist){
                WordList word = new WordList();
                wordListWords.add(words.get(x).toLowerCase());
                word.setWord(words.get(x).toLowerCase());
                wordListDao.create(word);
            }
            isExist = false;
        }

    }

    public void generateDocIndex(List<String> words, Integer fileID){
        List<String> temp = new ArrayList<>();
        for(int x = 0;x<words.size();x++){
            StringTokenizer st = new StringTokenizer(words.get(x)," ~`!@#$%^&*()-=_+[]{};'\\:|,./<>?");
            while(st.hasMoreTokens()){
                String word = st.nextToken();
                if(word.length()>1) {
                    temp.add(st.nextToken());
                }
            }
        }
        words = preprocessWords(temp);
        String[][] values = new String[words.size()][2];
        int counter = 0;
        boolean isExist = false;

        for(int x =0;x<words.size();x++){
            for(int y = 0;y<counter;y++){
                if(values[y][0].equalsIgnoreCase(words.get(x))){
                    values[y][1] = (Integer.parseInt(values[y][1])+1)+"";
                    isExist = true;
                }
            }
            if(!isExist){
                values[counter][0] = words.get(x);
                counter++;
            }
            isExist = false;
        }

        for(int x= 0;x<counter;x++){
            DocumentIndex di = new DocumentIndex();
            di.setFile(fileDao.show(fileID));
            di.setVectorCount(Integer.parseInt(values[counter][1]));
            di.setWordList(wordListDao.getID(values[counter][0]));
            documentIndexDao.create(di);
        }

    }

    public List<String> checkWords(List<String> wordList) {
        List<String> finalList = new ArrayList<>();
        for (int x = 0; x < wordList.size(); x++) {
            if (stemmerService.checkIfInDictionary(wordList.get(x).trim())) {
                finalList.add(wordList.get(x).toLowerCase().trim());
            }
        }
        return finalList;
    }

    private List<String> preprocessWords(List<String> wordList){
        wordList = checkWords(wordList);
        List<String> finalList = new ArrayList<>();
        for(int x = 0;x<wordList.size();x++){
            if(wordList.get(x).length()>1){
                finalList.add(wordList.get(x));
            }
        }
        return finalList;
    }

}
