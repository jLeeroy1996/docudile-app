package com.docudile.app.data.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by franc on 2/10/2016.
 */
@Entity
@Table(name = "folders", uniqueConstraints = @UniqueConstraint(columnNames = {"parent_folder_id", "name", "user_id"}))
public class Folder implements Serializable {

    @Id @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "parent_folder_id")
    private Folder folder;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
