package com.docudile.app.services;

import com.docudile.app.data.dto.CategoryDto;
import com.docudile.app.data.dto.FileContentDto;
import com.docudile.app.data.dto.WordListDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by cicct on 2/15/2016.
 */
public interface ContentClassificationService {

    public boolean train(Integer userID) throws IOException;

    public WordListDto getDistinctWords(List<FileContentDto> files, WordListDto wordList);

    public List<CategoryDto> countWords(List<FileContentDto> files, WordListDto wordList, List<CategoryDto> categories);

    public float[][] calculateNaiveBayes(WordListDto wordList, List<CategoryDto> categories);

    public Integer categorize(List<String> words, Integer userID, String filename);

    public String readDocxFile(String filename);

    public void writeToFile(MultipartFile[] f, String path) throws IOException;

    public void createCategory(String categoryName, Integer userID);

}
