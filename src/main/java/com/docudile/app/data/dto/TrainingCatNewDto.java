package com.docudile.app.data.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by franc on 3/6/2016.
 */
public class TrainingCatNewDto {

    private List<MultipartFile> file;

    public List<MultipartFile> getFile() {
        return file;
    }

    public void setFile(List<MultipartFile> file) {
        this.file = file;
    }

}
