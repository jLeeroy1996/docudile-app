package com.docudile.app.data.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cicct on 2/11/2016.
 */
public class FileContentDto implements Serializable{
    private String fileName;
    private Integer fileID;
    private double vector;
    private List<String> wordList;
    private String categoryName;

    public Integer getFileID() {
        return fileID;
    }

    public void setFileID(Integer fileID) {
        this.fileID = fileID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
