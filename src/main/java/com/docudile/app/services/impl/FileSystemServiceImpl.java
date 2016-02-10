package com.docudile.app.services.impl;

import com.docudile.app.data.dao.FileDao;
import com.docudile.app.data.dao.FolderDao;
import com.docudile.app.data.dao.UserDao;
import com.docudile.app.data.entities.File;
import com.docudile.app.data.entities.Folder;
import com.docudile.app.data.entities.User;
import com.docudile.app.services.DropboxService;
import com.docudile.app.services.FileSystemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * Created by franc on 2/10/2016.
 */
@Service("fileSystemService")
@Transactional
public class FileSystemServiceImpl implements FileSystemService {

    @Autowired
    private FolderDao folderDao;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DropboxService dropboxService;

    public boolean createFolder(String name, Integer parentId, Integer userId) {
        Folder folder = new Folder();
        User user = userDao.show(userId);
        folder.setName(name);
        folder.setParentFolder(folderDao.show(parentId));
        folder.setUser(user);
        if (folderDao.create(folder)) {
            String path = getPath(folder);
            return dropboxService.createFolder(path, user.getDropboxAccessToken());
        }
        return false;
    }

    public boolean storeFile(MultipartFile mfile, String path, Integer userId) {
        Folder folder = getFolderFromPath(path);
        User user = userDao.show(userId);
        String filename = mfile.getOriginalFilename();
        String filepath = path + "/" + filename;
        File file = new File();
        file.setFilename(filename);
        file.setUser(user);
        file.setFolder(folder);
        file.setDateUploaded(new Date().toString());
        if (fileDao.create(file)) {
            try {
                return dropboxService.uploadFile(filepath, mfile.getInputStream(), user.getDropboxAccessToken());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Set<Folder> getRootFolders(Integer userId) {
        return null;
    }

    public Folder getFolder(Integer id, Integer userId) {
        return null;
    }

    public List<Folder> getFolders(String name, Integer userId) {
        return null;
    }

    private String getPath(Folder folder) {
        return getPath(folder, "");
    }

    private String getPath(Folder folder, String path) {
        if (folder.getParentFolder() != null) {
            getPath(folder.getParentFolder(), folder.getName() + "/" + path);
        }
        return path;
    }

    private Folder getFolderFromPath(String path) {
        return getFolderFromPath(null, new LinkedList<String>(Arrays.asList(path.split("/"))));
    }

    private Folder getFolderFromPath(Folder base, LinkedList<String> path) {
        if (!path.isEmpty()) {
            String folderName = path.removeFirst();
            if (base == null) {
                base = folderDao.show(folderName);
                return getFolderFromPath(base, path);
            } else {
                if (base.getChildFolders() != null) {
                    for (Folder currFolder : base.getChildFolders()) {
                        if (currFolder.getName().equalsIgnoreCase(folderName)) {
                            return getFolderFromPath(currFolder, path);
                        }
                    }
                }
            }
        }
        return base;
    }

}
