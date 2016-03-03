package com.docudile.app.services.impl;

import com.docudile.app.data.dao.CategoryDao;
import com.docudile.app.data.dao.FileDao;
import com.docudile.app.data.dao.FolderDao;
import com.docudile.app.data.dao.UserDao;
import com.docudile.app.data.dto.FolderShowDto;
import com.docudile.app.data.dto.GeneralMessageResponseDto;
import com.docudile.app.data.dto.ModTagRequestDto;
import com.docudile.app.data.entities.Category;
import com.docudile.app.services.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by FrancAnthony on 3/2/2016.
 */
@Service("documentService")
@Transactional
@PropertySource({"classpath:/storage.properties"})
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private Environment environment;

    @Autowired
    private FileSystemService fileSystemService;

    @Autowired
    private DocumentStructureClassificationService docStructureClassification;

    @Autowired
    private ContentClassificationService contentClassificationService;

    @Autowired
    private DocxService docxService;

    @Autowired
    private TesseractService tesseractService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public FileSystemResource showFile(Integer id, String username) {
        String filePath = fileSystemService.download(id, userDao.show(username).getId());
        return new FileSystemResource(filePath);
    }

    @Override
    public GeneralMessageResponseDto classifyThenUpload(MultipartFile file, String username) {
        GeneralMessageResponseDto responseDto = new GeneralMessageResponseDto();
        List<String> text = getLines(file);
        if (text != null) {
            Map<Integer, String> tags = docStructureClassification.tag(environment.getProperty("storage.users") + username + "/" + environment.getProperty("storage.structure_tags"), text);
            String result = docStructureClassification.classify(StringUtils.join(tags, " "), environment.getProperty("storage.classifier"));
            Integer contentResult = contentClassificationService.categorize(text,userDao.show(username).getId(),file.getOriginalFilename());
            //ang gi return ani kay ang categoryID hehe thanks
        } else {
            responseDto.setMessage("error_reading_file");
        }
        return responseDto;
    }

    @Override
    public GeneralMessageResponseDto deleteFile(Integer id, String username) {
        GeneralMessageResponseDto responseDto = new GeneralMessageResponseDto();
        if (fileSystemService.delete(id, userDao.show(username).getId())) {
            responseDto.setMessage("file_deleted_successfully");
        }
        responseDto.setMessage("file_deletion_failed");
        return responseDto;
    }

    @Override
    public List<FolderShowDto> showRoot(String username) {
        return fileSystemService.getRootFolders(userDao.show(username).getId());
    }

    @Override
    public FolderShowDto showFolder(Integer id, String username) {
        return fileSystemService.getFolder(id, userDao.show(username).getId());
    }

    @Override
    public GeneralMessageResponseDto trainTag(ModTagRequestDto request, String username) {
        GeneralMessageResponseDto response = new GeneralMessageResponseDto();
        String path = environment.getProperty("storage.users") + username + "/" + environment.getProperty("storage.structure_tags");
        if (!docStructureClassification.trainTagger(path, request.getName(), request.getData())) {
            response.setMessage("problem_in_saving");
        } else {
            response.setMessage("training_successfully_saved");
        }
        return response;
    }

    @Override
    public GeneralMessageResponseDto contentTrain(String username) throws IOException {
        GeneralMessageResponseDto response = new GeneralMessageResponseDto();

        boolean noError = contentClassificationService.train(userDao.show(username).getId());

        if(noError){
            response.setMessage("training_successfully_saved");
        }
        else{
            response.setMessage("problem_in_training");
        }
        return response;

    }

    @Override
    public GeneralMessageResponseDto uploadTraining(MultipartFile file, String username, String categoryName) {
        GeneralMessageResponseDto response = new GeneralMessageResponseDto();

        Category cat = null;

        cat.setCategoryName(categoryName);
        cat.setUser(userDao.show(username));
        categoryDao.create(cat);

        if (fileSystemService.storeFileNotMapped(file, environment.getProperty("storage.content_training")+"/categoryName", userDao.show(username).getId())) {
            response.setMessage("upload_training_data_success");
        } else {
            response.setMessage("upload_failed");
        }
        return response;
    }

    @Override
    public GeneralMessageResponseDto deleteTag(String tagName, String username) {
        GeneralMessageResponseDto response = new GeneralMessageResponseDto();
        String path = environment.getProperty("storage.users") + username + "/" + environment.getProperty("storage.structure_tags") + tagName;
        if (docStructureClassification.delete(path)) {
            response.setMessage("tag_deleted");
        } else {
            response.setMessage("tag_delete_failed");
        }
        return response;
    }

    @Override
    public GeneralMessageResponseDto trainClassifier(String name, MultipartFile file, String username) {
        GeneralMessageResponseDto response = new GeneralMessageResponseDto();
        String path = environment.getProperty("storage.users") + username + "/" + environment.getProperty("storage.classifier");
        List<String> text = getLines(file);
        List<String> tags = new ArrayList<>(docStructureClassification.tag(environment.getProperty("storage.users") + username + "/" + environment.getProperty("storage.structure_tags"), text).values());
        boolean noError = docStructureClassification.trainClassifier(path, tags, name);
        if (!noError) {
            response.setMessage("problem_in_saving");
        } else {
            response.setMessage("training_successfully_saved");
        }
        return response;
    }



    private File multipartToFile(MultipartFile mFile) {
        File file = new File(mFile.getOriginalFilename());
        try {
            mFile.transferTo(file);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> getLines(MultipartFile mfile) {
        List<String> text = null;
        String extension = FilenameUtils.getExtension(mfile.getOriginalFilename());
        if (extension.equals(".docx")) {
            text = docxService.readDocx(multipartToFile(mfile));
        } else try {
            ImageIO.read(mfile.getInputStream()).toString();
            text = tesseractService.doOCR(multipartToFile(mfile));
        } catch (IOException e) {
        }
        return text;
    }

    private String getYear(String line) {
        Pattern pattern = Pattern.compile("\\d{4}");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private String getFromMemo(String line) {
        Pattern pattern = Pattern.compile("(i?)From: (.+)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return null;
    }

    private String getToMemo(String line) {
        Pattern pattern = Pattern.compile("(i?)To: (.+)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return null;
    }

}
