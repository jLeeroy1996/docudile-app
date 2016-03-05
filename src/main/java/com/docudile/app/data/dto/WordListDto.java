package com.docudile.app.data.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cicct on 2/11/2016.
 */
public class WordListDto implements Serializable{
    private List<String> wordList;
    private int count;

    public WordListDto(List<String> wordList) {
        this.wordList = wordList;
    }

    public WordListDto() {

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getWordList() {
        return wordList;
    }

    public void setWordList(List<String> wordList) {
        this.wordList = wordList;
    }

    public void addWord(String word){
        this.wordList.add(word);
    }

}
