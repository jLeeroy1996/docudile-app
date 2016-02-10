package com.docudile.app.services.impl;

import com.docudile.app.data.dto.UploadResponseDto;
import com.docudile.app.services.DocumentService;
import com.docudile.app.services.DropboxService;
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
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DropboxService dropboxService;

    public ResponseEntity<UploadResponseDto> upload(MultipartFile document) {
        UploadResponseDto response = new UploadResponseDto();
        if (!document.isEmpty()) {
            response.setMessage("document_uploaded_successfully");
            return new ResponseEntity<UploadResponseDto>(response, HttpStatus.OK);
        }
        response.setMessage("document_empty");
        return new ResponseEntity<UploadResponseDto>(response, HttpStatus.BAD_REQUEST);
    }

}
