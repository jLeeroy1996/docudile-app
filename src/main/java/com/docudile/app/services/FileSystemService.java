package com.docudile.app.services;

import com.docudile.app.data.dto.FolderShowDto;
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

    public boolean storeFileNotMapped(MultipartFile file, String path, Integer userId);

    public List<FolderShowDto> getRootFolders(Integer userId);

    public FolderShowDto getFolder(Integer id, Integer userId);

    public String download(Integer id, Integer userId);

    public boolean delete(Integer id, Integer userId);

    public void createFolderFromCategory(String displayName, Integer userId);

    public void createCategoryFolders(Folder f, String categoryName, Integer userId);

}
