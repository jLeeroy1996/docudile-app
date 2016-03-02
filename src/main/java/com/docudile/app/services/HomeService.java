package com.docudile.app.services;

import com.docudile.app.data.dto.GeneralMessageResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by franc on 2/10/2016.
 */
public interface HomeService {

    public ResponseEntity<GeneralMessageResponseDto> uploadDoc(MultipartFile document, String username);

//    public ResponseEntity<GeneralMessageResponseDto> submitNewType(MultipartFile[] document, String typeName, String username);

}
