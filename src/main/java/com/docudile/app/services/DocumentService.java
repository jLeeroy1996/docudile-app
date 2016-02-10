package com.docudile.app.services;

import com.docudile.app.data.dto.UploadResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by franc on 2/10/2016.
 */
public interface DocumentService {

    public ResponseEntity<UploadResponseDto> upload(MultipartFile document);

}
