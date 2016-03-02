package com.docudile.app.services;

import com.docudile.app.data.dto.FolderShowDto;
import com.docudile.app.data.dto.GeneralMessageResponseDto;
import com.docudile.app.data.dto.ModTagRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by FrancAnthony on 3/2/2016.
 */
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private FileSystemService fileSystemService;

    @Autowired
    private DocumentStructureClassificationService docStructureClassification;

    @Autowired
    private DocxService docxService;

    @Autowired
    private TesseractService tesseractService;



    @Override
    public FileSystemResource showFile(Integer id, String username) {
        return null;
    }

    @Override
    public GeneralMessageResponseDto classifyThenUpload(MultipartFile file, String username) {
        return null;
    }

    @Override
    public GeneralMessageResponseDto deleteFile(Integer id, String username) {
        return null;
    }

    @Override
    public List<FolderShowDto> showRoot() {
        return null;
    }

    @Override
    public FolderShowDto showFolder(Integer id, String username) {
        return null;
    }

    @Override
    public GeneralMessageResponseDto trainTag(List<ModTagRequestDto> request, String username) {
        return null;
    }

    @Override
    public GeneralMessageResponseDto deleteTag(String tagName, String username) {
        return null;
    }

    @Override
    public GeneralMessageResponseDto trainClassifier(String name, MultipartFile file, String username) {
        return null;
    }
}
