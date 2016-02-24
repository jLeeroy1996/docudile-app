package com.docudile.app.data.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cicct on 2/11/2016.
 */
public class FileContentDto implements Serializable{
    private String filePath;
    private double vector;
    private List<String> wordList;
    private String categoryName;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public double getVector() {
        return vector;
    }

    public void setVector(double vector) {
        this.vector = vector;
    }

    public List<String> getWordList() {
        return wordList;
    }

    public void setWordList(List<String> wordList) {
        this.wordList = wordList;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
