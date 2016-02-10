package com.docudile.app.services;

import com.docudile.app.data.entities.Folder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

/**
 * Created by franc on 2/10/2016.
 */
public interface FileSystemService {

    public boolean createFolder(String name, Integer parentId, Integer userId);

    public boolean storeFile(MultipartFile file, String path, Integer userId);

    public List<Folder> getRootFolders(Integer userId);

    public Folder getFolder(Integer id, Integer userId);

    public List<Folder> getFolders(String name, Integer userId);

}
