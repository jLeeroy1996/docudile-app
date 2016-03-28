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

import java.io.*;
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

    public List<File> search(Integer userId, String searchString) {
        boolean isExist = false;
        System.out.println("niagi ko");
        List<File> documentID = new ArrayList<>();
        List<File> files = fileDao.getSpecificFiles(userId);
        List<DocumentIndex> documents = new ArrayList<>();
        WordList wordList;
        StringTokenizer st = new StringTokenizer(searchString," ");
        List<Integer> search = new ArrayList<>();
        while(st.hasMoreTokens()){
            String word = st.nextToken();
            if(wordListDao.getID(word) != null) {
                search.add(wordListDao.getID(word).getId());
            }
        }

        List<Integer[]> values = documentIndexDao.getID(files,search);
        Integer[] temp = new Integer[search.size()+2];
        for(int x = 0; x<values.size();x++) {
            for (int y = 0; y < values.size()-1; y++) {
                if(values.get(y+1)[1] > values.get(y)[1]) {
                    temp[0] = values.get(y)[0];
                    temp[1] = values.get(y)[1];
                    values.get(y)[0] = values.get(y+1)[0];
                    values.get(y)[1] = values.get(y+1)[1];
                    values.get(y+1)[0] = temp[0];
                    values.get(y+1)[1] = temp[1];
                    for (int z = 2; z < values.get(x).length; z++) {
                        temp[z] = values.get(y)[z];
                        values.get(y)[z] = values.get(y+1)[z];
                        values.get(y+1)[z] = temp[z];
                    }
                }
                if(values.get(y+1)[1] == values.get(y)[1]){
                    int sum1 = 0;
                    int sum2 = 0;
                    for (int z = 2; z < values.get(x).length; z++) {
                        sum1+=values.get(y)[z];
                        sum2+=values.get(y+1)[z];
                    }
                    if(sum2 > sum1){
                        temp[0] = values.get(y)[0];
                        temp[1] = values.get(y)[1];
                        values.get(y)[0] = values.get(y+1)[0];
                        values.get(y)[1] = values.get(y+1)[1];
                        values.get(y+1)[0] = temp[0];
                        values.get(y+1)[1] = temp[1];
                        for (int z = 2; z < values.get(x).length; z++) {
                            temp[z] = values.get(y)[z];
                            values.get(y)[z] = values.get(y+1)[z];
                            values.get(y+1)[z] = temp[z];
                        }
                    }
                }

            }
        }


        /*while (st.hasMoreTokens()) {
            String word = st.nextToken();
            wordList = wordListDao.getID(word);
            List<DocumentIndex> documents = documentIndexDao.getID(files, wordList.getId());
            for(int x = 0; x < documents.size(); x++) {
                if(documents.get(x) == null){
                    continue;
                }
                for(int y = 0;y<documentID.size();y++){
                    if(documentID.get(y).getFile().getId().equals(documents.get(x).getFile().getId())){
                        isExist = true;
                    }
                }
                if(!isExist) {
                    documentID.add(documents.get(x));
                    System.out.println(documents.get(x) + "xxxxxxxxxxxx");
                }
                isExist = false;
            }
        }
*/
        for(int x = 0;x<values.size();x++){
            System.err.println(values.get(x)[0] + " " + values.get(x)[1]);
            if(values.get(x)[1] > 0) {
                documentID.add(fileDao.show(values.get(x)[0]));
            }
        }

        return documentID;
    }

    public void generateWordList(List<String> words, Integer userId){
        List<String> temp = new ArrayList<>();
        for(int x = 0;x<words.size();x++){
            StringTokenizer st = new StringTokenizer(words.get(x).toLowerCase().trim()," ~`!@#$%^&*()-=_+[]{};'\\:|,./<>?");
            while(st.hasMoreTokens()){
                String word = st.nextToken();
                if(word.length()>1) {
                    temp.add(word);
                }
            }
        }
        words = preprocessWords(temp);


        List<String> wordListWords = wordListDao.getWords();

        boolean isExist = false;


        for(int x = 0;x<words.size();x++){
            for(int y=  0;y<wordListWords.size();y++){
                if(words.get(x).trim().equalsIgnoreCase(wordListWords.get(y).trim())){
                    isExist = true;
                    break;
                }
            }
            if(!isExist){
                WordList word = new WordList();
                wordListWords.add(words.get(x).toLowerCase().trim());
                word.setWord(words.get(x).toLowerCase().trim());
                wordListDao.create(word);
            }
            isExist = false;
        }

    }

    public void generateDocIndex(List<String> words, Integer fileID) throws IOException {
        List<String> temp = new ArrayList<>();
        for(int x = 0;x<words.size();x++){
            StringTokenizer st = new StringTokenizer(words.get(x)," ~`!@#$%^&*()-=_+[]{};'\\:|,./<>?");
            while(st.hasMoreTokens()){
                String word = st.nextToken();
                if(word.length()>1) {
                    temp.add(word);
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
                values[counter][1] = "1";
                counter++;
            }
            isExist = false;
        }
        for(int x= 0;x<counter;x++){
            DocumentIndex di = new DocumentIndex();
            di.setFile(fileDao.show(fileID));
            di.setVectorCount(Integer.parseInt(values[x][1]));
            di.setWordList(wordListDao.getID(values[x][0]));
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
