package com.docudile.app.services.impl;

import com.docudile.app.data.dao.UserDao;
import com.docudile.app.data.dto.UploadResponseDto;
import com.docudile.app.services.HomeService;
import com.docudile.app.services.FileSystemService;
import com.docudile.app.services.TesseractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by franc on 2/10/2016.
 */
@Service("documentService")
@Transactional
public class HomeServiceImpl implements HomeService {

    @Autowired
    private FileSystemService fileSystemService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TesseractService tesseractService;

    public ResponseEntity<UploadResponseDto> uploadDoc(MultipartFile document, String username) {
        UploadResponseDto response = new UploadResponseDto();
        if (!document.isEmpty()) {
            fileSystemService.storeFile(document, "Memo/2016/Excuse/", userDao.show(username).getId());
            response.setMessage("document_uploaded_successfully");
            return new ResponseEntity<UploadResponseDto>(response, HttpStatus.OK);
        }
        response.setMessage("document_empty");
        return new ResponseEntity<UploadResponseDto>(response, HttpStatus.BAD_REQUEST);
    }

//    public ResponseEntity<UploadResponseDto> submitNewType(MultipartFile[] document, String typeName, String username) {
//        UploadResponseDto response = new UploadResponseDto();
//
//    }

}
