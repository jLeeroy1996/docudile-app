package com.docudile.app.services.impl;

import com.docudile.app.data.dao.CategoryDao;
import com.docudile.app.data.dao.FileDao;
import com.docudile.app.data.dao.UserDao;
import com.docudile.app.data.dao.WordListDao;
import com.docudile.app.data.dto.CategoryDto;
import com.docudile.app.data.dto.FileContentDto;
import com.docudile.app.data.dto.WordListDto;
import com.docudile.app.data.entities.Category;
import com.docudile.app.data.entities.User;
import com.docudile.app.services.ContentClassificationService;
import com.docudile.app.services.DocxService;
import com.docudile.app.services.DropboxService;
import com.docudile.app.services.FileSystemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by cicct on 2/15/2016.
 */
@Service("contentClassificationService")
@Transactional
public class ContentClassificationServiceImpl implements ContentClassificationService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private FileSystemService fileSystemService;

    @Autowired
    private DocxService docxService;

    @Autowired
    private WordListDao wordListDao;

    public boolean train(Integer userID) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        WordListDto wordList = null;
        List<FileContentDto> fileDto = null;
        List<com.docudile.app.data.entities.File> file = null;
        CategoryDto category = new CategoryDto();
        FileContentDto fileContentDto = new FileContentDto();
        List<CategoryDto> categoriesDto = null;


        //get Categories from DB
        List<Category> categories = categoryDao.getCategories(userID);

        for (int x = 0; x < categories.size(); x++) {
            //instantiate a Category Class ( new Category(categoryName,fileCount) )
            category.setName(categories.get(x).getCategoryName());
            category.setFileCount(fileDao.numberOfFiles(categories.get(x).getId()));
            category.setCategoryID(categories.get(x).getId());
            //add it into List<Category> categories
            categoriesDto.add(category);
        }

        //get Access Token
        User user = userDao.getUserDetails(userID);


        //get Files from each Category in DropBox
        file = fileDao.getSpecificFiles(userID);

        // convert to FileContentDto
        for (int x = 0; x < file.size(); x++) {
            fileContentDto.setFileName(file.get(x).getFilename());
            fileContentDto.setFileID(file.get(x).getId());
            for (int y = 0; y < categoriesDto.size(); y++) {
                if (categoriesDto.get(y).getCategoryID() == file.get(x).getId()) {
                    fileContentDto.setCategoryName(categoriesDto.get(y).getName());
                }
            }
            //foreach files in the category, open the documents, split the words within the documents, put into List<String>
            String path = fileSystemService.download(fileContentDto.getFileID(),userID);
            File f = new File(path);
            fileContentDto.setWordList(docxService.readDocx(f));
            //add it into List<Files> files
            fileDto.add(fileContentDto);
        }




        //get wordList in DB
        wordList.setWordList(wordListDao.getWords());
        wordList.setCount(wordListDao.getWords().size());


        //get updated wordList
        wordList = getDistinctWords(fileDto, wordList);
        //end

        //count words
        categoriesDto = countWords(fileDto, wordList, categoriesDto);
        //end
        mapper.writeValue(new File("D://"), categoriesDto);
        //get vectors
        float[][] wordListVectors = calculateNaiveBayes(wordList, categoriesDto);
        //end


        //put to DB si wordListVectors

        return false;
    }

    public WordListDto getDistinctWords(List<FileContentDto> files, WordListDto list) {
        List<String> wordListWords = list.getWordList();
        for (int x = 0; x < files.size(); x++) {
            List<String> words = files.get(x).getWordList();
            for (int y = 0; y < words.size(); y++) {
                for (int z = 0; z < wordListWords.size(); z++) {
                    if (!(words.get(y).equalsIgnoreCase(wordListWords.get(z)))) {
                        wordListWords.add(words.get(y));

                    }
                }
            }
        }
        WordListDto wordList = new WordListDto(wordListWords);
        return wordList;
    }

    public List<CategoryDto> countWords(List<FileContentDto> files, WordListDto wordList, List<CategoryDto> categories) {

        String[][] countWords = new String[wordList.getWordList().size()][2];
        List<String> wordListWords = wordList.getWordList();
        List<String> filesWordList = null;
        int counter = 0;

        for (int x = 0; x < wordListWords.size(); x++) {
            countWords[x][0] = wordListWords.get(x);
            countWords[x][1] = "0";
        }

        for (int x = 0; x < categories.size(); x++) {
            categories.get(x).setWordList(countWords);
        }

        for (int x = 0; x < files.size(); x++) {
            filesWordList = files.get(x).getWordList();
            for (int y = 0; y < categories.size(); y++) {
                if (categories.get(y).getName().equalsIgnoreCase(files.get(x).getCategoryName())) {
                    counter = y;
                }
            }
            countWords = categories.get(counter).getWordList();
            for (int a = 0; a < filesWordList.size(); a++) {
                for (int z = 0; z < wordListWords.size(); z++) {
                    if (filesWordList.get(a).equalsIgnoreCase(wordListWords.get(z))) {
                        categories.get(counter).addWordCount();
                        countWords[z][1] = (Integer.parseInt(countWords[z][1]) + 1) + "";
                    }
                }
            }
            categories.get(counter).setWordList(countWords);

        }


        return categories;
    }

    public float[][] calculateNaiveBayes(WordListDto wordList, List<CategoryDto> categories) {
        List<String> wordListWords = wordList.getWordList();
        float[][] wordListVectors = new float[wordListWords.size()][categories.size()];
        int numOfDistinctWords = wordListWords.size();
        for (int x = 0; x < categories.size(); x++) {
            String[][] tempCategoryVector = categories.get(x).getWordList();
            for (int y = 0; y < wordListWords.size(); y++) {
                wordListVectors[y][x] = ((float) (Float.parseFloat(tempCategoryVector[y][1]) + 1)) / ((float) (categories.get(x).getWordCount() + numOfDistinctWords));
            }
        }
        return wordListVectors;
    }

    public String categorize(List<String> words) {
        List<CategoryDto> categories = null;
        //get data from DB Category
        //instantiate a Category Class ( new Category(categoryName,fileCount) )
        //add Categories in List<Category> categories
        int totalFiles = 0;
        float[] categoryVectors = new float[categories.size()];

        for (int x = 0; x < categories.size(); x++) {
            totalFiles += categories.get(x).getFileCount();
        }

        for (int x = 0; x < categories.size(); x++) {
            categoryVectors[x] = categories.get(x).getFileCount() / totalFiles;
        }


        return "";
    }

    public String readDocxFile(String fileName) {
        String sentence = "";
        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            XWPFDocument document = new XWPFDocument(fis);

            List<XWPFParagraph> paragraphs = document.getParagraphs();

            for (XWPFParagraph para : paragraphs) {
                sentence += para.getText();
            }
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sentence;
    }

}