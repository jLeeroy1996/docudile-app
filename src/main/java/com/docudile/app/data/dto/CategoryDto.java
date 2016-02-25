package com.docudile.app.data.dto;

import java.io.Serializable;

/**
 * Created by cicct on 2/10/2016.
 */
public class CategoryDto implements Serializable {

    private String name;
    private int categoryID;
    private int fileCount;
    private int wordCount = 0;
    private String[][] wordList;

    public String[][] getWordList() {
        return wordList;
    }

    public void setWordList(String[][] wordList) {
        this.wordList = wordList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public void addWordCount() {
        this.wordCount++;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
