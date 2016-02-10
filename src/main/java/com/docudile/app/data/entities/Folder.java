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
    private Folder parentFolder;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "parentFolder", fetch = FetchType.LAZY)
    private Set<Folder> childFolders;

    @OneToMany(mappedBy = "folder", fetch = FetchType.LAZY)
    private Set<File> files;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Folder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(Folder parentFolder) {
        this.parentFolder = parentFolder;
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

    public Set<Folder> getChildFolders() {
        return childFolders;
    }

    public void setChildFolders(Set<Folder> childFolders) {
        this.childFolders = childFolders;
    }

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

}
