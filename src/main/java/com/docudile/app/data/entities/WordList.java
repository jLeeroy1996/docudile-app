package com.docudile.app.data.entities;


import javax.persistence.*;

/**
 * Created by cicct on 2/24/2016.
 */

@Entity
@Table(name = "wordLists", indexes = {@Index(name = "idx", columnList = "word")})
public class WordList
{

    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    @Column(name="word",length = 100)
    private String word;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
