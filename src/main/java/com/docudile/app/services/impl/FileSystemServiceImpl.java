package com.docudile.app.services.impl;

import com.docudile.app.data.dao.CategoryDao;
import com.docudile.app.data.dao.FileDao;
import com.docudile.app.data.dao.FolderDao;
import com.docudile.app.data.dao.UserDao;
import com.docudile.app.data.dto.FileShowDto;
import com.docudile.app.data.dto.FolderShowDto;
import com.docudile.app.data.entities.*;
import com.docudile.app.services.DropboxService;
import com.docudile.app.services.FileSystemService;
import com.docudile.app.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

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

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserService userService;

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

    public boolean storeFile(MultipartFile mfile, String path, Integer userId, Integer contentID) {
        Folder folder = getFolderFromPath(path);
        User user = userDao.show(userId);
        Category cat = categoryDao.getCategory(contentID);
        String filename = mfile.getOriginalFilename();
        String filepath = path + "/" + filename;
        System.err.println("File path: " + filepath);
        File file = new File();
        file.setFilename(filename);
        file.setUser(user);
        file.setCategory(cat);
        file.setFolder(folder);
        file.setDateUploaded(convertDateToString(new Date()));
        file.setId(fileDao.getFileID(filename,user.getId()).getId());
        if (fileDao.update(file)) {
            try {
                return dropboxService.uploadFile(filepath, mfile.getInputStream(), user.getDropboxAccessToken());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean storeFileNotMapped(MultipartFile file, String path, Integer userId) {
        String filepath = path + file.getOriginalFilename();
        try {
            return dropboxService.uploadFile(filepath, file.getInputStream(), userDao.show(userId).getDropboxAccessToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<FolderShowDto> getRootFolders(Integer userId) {
        List<FolderShowDto> folders = new ArrayList<FolderShowDto>();
        for (Folder folder : folderDao.root(userId)) {
            folders.add(convertToDto(folder));
        }
        return folders;
    }

    public FolderShowDto getFolder(Integer id, Integer userId) {
        Folder folder = folderDao.show(id);
        if (folder.getUser().getId() == userId) {
            return convertToDto(folder);
        }
        return null;
    }

    public String download(Integer id, Integer userId) {
        File file = fileDao.show(id);
        if (file.getUser().getId() == userId) {
            String filepath = getPath(file.getFolder()) + "/" + file.getFilename();
            return dropboxService.getFile(filepath, userDao.show(userId).getDropboxAccessToken());
        }
        return null;
    }

    @Override
    public boolean delete(Integer id, Integer userId) {
        File file = fileDao.show(id);
        if (file.getUser().getId() == userId) {
            String filepath = getPath(file.getFolder()) + "/" + file.getFilename();
            return dropboxService.deleteFile(filepath, userDao.show(userId).getDropboxAccessToken());
        }
        return false;
    }

    @Override
    public void createFolderFromCategory(String displayName, Integer userId) {
        List<Folder> listFrom = folderDao.showAllByName("from", userId);
        List<Folder> listTo = folderDao.showAllByName("to", userId);
        for(Folder f : listFrom) {
            createCategoryFolders(f, displayName, userId);
            System.out.println(getPath(f) + displayName);
            dropboxService.createFolder("/" + getPath(f) + displayName, userDao.show(userId).getDropboxAccessToken());
        }
        for(Folder f : listTo) {
            createCategoryFolders(f, displayName, userId);
            dropboxService.createFolder("/" + getPath(f) + displayName, userDao.show(userId).getDropboxAccessToken());
        }
    }

    @Override
    public void createCategoryFolders(Folder f, String categoryName, Integer userId) {
        Folder cat = new Folder();
        cat.setName(categoryName);
        cat.setUser(userDao.show(userId));
        cat.setParentFolder(f);
        folderDao.create(cat);
    }

    @Override
    public List<FileShowDto> getFilesFromId(List<WordListDocument> documentId, Integer userId) {
        List<FileShowDto> files = new ArrayList<>();
        for(WordListDocument id : documentId) {
            files.add(convertToDto(fileDao.show(id.getFile().getId())));
        }
        return files;
    }

    private String getPath(Folder folder) {
        return getPath(folder, "");
    }

    private String getPath(Folder folder, String path) {
        if (folder.getParentFolder() != null) {
            return getPath(folder.getParentFolder(), folder.getName() + "/" + path);
        }
        return folder.getName() + "/" + path;
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

    private FolderShowDto convertToDto(Folder folder) {
        FolderShowDto dto = new FolderShowDto();
        dto.setId(folder.getId());
        dto.setName(folder.getName());
        if (folder.getParentFolder() != null) {
            dto.setParentFolder(folder.getParentFolder().getName());
        }
        dto.setUser(userService.convert(folder.getUser()));
        List<FolderShowDto> childFolders = new ArrayList<FolderShowDto>();
        for (Folder childFolder : folder.getChildFolders()) {
            childFolders.add(convertToDto(childFolder));
        }
        dto.setChildFolders(childFolders);
        List<FileShowDto> files = new ArrayList<FileShowDto>();
        for (File file : folder.getFiles()) {
            files.add(convertToDto(file));
        }
        dto.setFiles(files);
        dto.setDateModified(convertDateToString(findLatestDate(folder)));
        dto.setPath(getPath(folder));
        return dto;
    }

    private FileShowDto convertToDto(File file) {
        FileShowDto dto = new FileShowDto();
        dto.setId(file.getId());
        dto.setFilename(file.getFilename());
        dto.setPath(getPath(file.getFolder()));
        dto.setDateUploaded(file.getDateUploaded());
        dto.setUser(userService.convert(file.getUser()));
        return dto;
    }

    private Date findLatestDate(Folder folder) {
        return findLatestDate(folder, null);
    }

    private Date findLatestDate(Folder folder, Date date) {
        for (File file : folder.getFiles()) {
            Date currDate = convertStringToDate(file.getDateUploaded());
            if (currDate != null && date != null) {
                if (currDate.after(date)) {
                    date = currDate;
                }
            }
            if (date == null) {
                date = currDate;
            }
        }
        for (Folder childFolder : folder.getChildFolders()) {
            date = findLatestDate(childFolder, date);
        }
        return date;
    }

    private Date convertStringToDate(String date) {
        if (StringUtils.isNotEmpty(date)) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy, E");
            formatter.setLenient(false);
            try {
                return formatter.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String convertDateToString(Date date) {
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy, E");
            return formatter.format(date);
        }
        return "Not Yet Modified";
    }

    public boolean createFoldersFromPath(String path, Integer userId) {
        if(createFoldersFromPath(null, new LinkedList<String>(Arrays.asList(path.split("/"))), userId)) {
            return true;
        }
        return false;
    }

    public boolean createFoldersFromPath(Folder base, LinkedList<String> folders, Integer userId) {
        Folder isExist = null;
        if(!folders.isEmpty()) {
            String folderName = folders.removeFirst();
            if(base == null) {
                isExist = folderDao.show(folderName);
            } else {
                isExist = folderDao.show(folderName, base.getId());
            }
            if(isExist != null) {
                base = isExist;
                createFoldersFromPath(base, folders, userId);
                return true;
            } else {
                Folder newFolder = createFolder(base, folderName, userId);
                base = newFolder;
                createFoldersFromPath(base, folders, userId);
                return true;
            }
        }
        return false;
    }

    private Folder createFolder(Folder parent, String folderName, Integer userId) {
        Folder f = new Folder();
        f.setUser(userDao.show(userId));
        f.setName(folderName);
        f.setParentFolder(parent);
        if(folderDao.create(f))
            return f;
        return null;
    }

}
