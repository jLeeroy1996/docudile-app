package com.docudile.app.services;

import com.docudile.app.data.dto.UploadResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by franc on 2/10/2016.
 */
public interface HomeService {

    public ResponseEntity<UploadResponseDto> uploadDoc(MultipartFile document, String username);

//    public ResponseEntity<UploadResponseDto> submitNewType(MultipartFile[] document, String typeName, String username);

}
