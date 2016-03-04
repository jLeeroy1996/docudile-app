package com.docudile.app.services;

import com.docudile.app.data.dto.FolderShowDto;
import com.docudile.app.data.dto.GeneralMessageResponseDto;
import com.docudile.app.data.dto.ModTagRequestDto;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by franc on 2/9/2016.
 */
public interface DocumentService {

    public FileSystemResource showFile(Integer id, String username);

    public GeneralMessageResponseDto classifyThenUpload(MultipartFile file, String username);

    public GeneralMessageResponseDto deleteFile(Integer id, String username);

    public GeneralMessageResponseDto contentTrain(String username, MultipartFile file, String categoryName) throws IOException;

    public GeneralMessageResponseDto uploadTraining(MultipartFile file, String username, String categoryName);

    public List<FolderShowDto> showRoot(String username);

    public FolderShowDto showFolder(Integer id, String username);

    public GeneralMessageResponseDto trainTag(List<ModTagRequestDto> requests, String username);

    public GeneralMessageResponseDto deleteTag(String tagName, String username);

    public GeneralMessageResponseDto trainClassifier(String name, MultipartFile file, String username);

    public GeneralMessageResponseDto sampleTrainContent() throws IOException;

}
