package com.docudile.app.data.entities;

/**
 * Created by cicct on 2/14/2016.
 */

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category{

    @Id @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "categoryName")
    private String categoryName;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "numberOfFiles")
    private Integer numberOfFiles;

    @Column(name = "structure")
    private String structureType;

    public Integer getNumberOfFiles() {
        return numberOfFiles;
    }

    public void setNumberOfFiles(Integer numberOfFiles) {
        this.numberOfFiles = numberOfFiles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
