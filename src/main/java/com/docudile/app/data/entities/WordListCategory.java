package com.docudile.app.data.entities;

import javax.persistence.*;

/**
 * Created by cicct on 3/1/2016.
 */
@Entity
@Table(name="wordListsCategory")
public class WordListCategory {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="wordListID")
    private WordList wordList;

    @ManyToOne
    @JoinColumn(name="categoryID")
    private Category category;

    @Column(name="count")
    private Float count;

    public WordList getWordList() {
        return wordList;
    }

    public void setWordList(WordList wordList) {
        this.wordList = wordList;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Float getCount() {
        return count;
    }

    public void setCount(Float count) {
        this.count = count;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
