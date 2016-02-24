package com.docudile.app.data.entities;

import javax.persistence.*;

/**
 * Created by cicct on 2/24/2016.
 */
@Entity
@Table(name="wordListDocuments")
public class WordListDocument {


    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="wordListID")
    private WordList wordList;

    @ManyToOne
    @JoinColumn(name="fileID")
    private File file;

    @Column(name="count")
    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public WordList getWordList() {
        return wordList;
    }

    public void setWordList(WordList wordList) {
        this.wordList = wordList;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Integer getCount() {
        return count;
    }

    public void setVectorCount(Integer count) {
        this.count = count;
    }
}
