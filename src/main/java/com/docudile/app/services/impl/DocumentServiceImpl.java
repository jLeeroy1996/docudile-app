package com.docudile.app.services.impl;

import com.docudile.app.data.dao.CategoryDao;
import com.docudile.app.data.dao.FolderDao;
import com.docudile.app.data.dao.UserDao;
import com.docudile.app.data.dto.FolderShowDto;
import com.docudile.app.data.dto.GeneralMessageResponseDto;
import com.docudile.app.data.dto.ModTagRequestDto;
import com.docudile.app.data.entities.Category;
import com.docudile.app.data.entities.Folder;
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
    private AspriseOCRService aspriseOCRService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private FolderDao folderDao;

    @Autowired
    private DropboxService dropboxService;

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
            String type = docStructureClassification.classify(StringUtils.join(tags.values(), " "), environment.getProperty("storage.users") + username + "/" + environment.getProperty("storage.classifier") + "/processed");
            //Integer contentResult = contentClassificationService.categorize(getLinesContent(file), userDao.show(username).getId(), file.getOriginalFilename());
            String path;
            String year = "";
            String from = "";
            String to = "";
            boolean fromHome = false;
            boolean fromOthers = false;
            for (Integer key : tags.keySet()) {
                String curr = tags.get(key);
                if (curr.equals("DATE") || curr.equals("TO_DATE")) {
                    year = getYear(text.get(key));
                } else if (!fromHome && !fromOthers) {
                    if (curr.equals("Office")) {
                        String line = text.get(key);
                        if (line.equalsIgnoreCase("College of Information, Computer, and Communications Technology") || line.equalsIgnoreCase("CICCT")) {
                            fromHome = true;
                        } else {
                            fromOthers = true;
                        }
                    }
                }
                if (fromHome) {
                    if (type.equalsIgnoreCase("memo")) {
                        if (curr.equals("TO")) {
                            to = getToMemo(text.get(key));
                        }
                    } else if (type.equalsIgnoreCase("letter")) {
                        if (tags.get(key - 1).startsWith("Person") && tags.get(key).startsWith("Office")) {
                            to = tags.get(key).split("-")[0];
                        }
                    }
                } else if (fromOthers) {
                    if (type.equalsIgnoreCase("memo")) {
                        if (curr.equals("FROM")) {
                            from = getFromMemo(text.get(key));
                        }
                    } else if (type.equalsIgnoreCase("letter")) {
                        if (tags.get(key - 1).startsWith("Person") && tags.get(key).startsWith("Office")) {
                            from = tags.get(key).split("-")[0];
                        }
                    }
                }
            }
            if (StringUtils.isNotEmpty(year)) {
                path = year;
                if (StringUtils.isNotEmpty(type)) {
                    path += "/" + type;
                    //String category = categoryDao.show(contentResult).getCategoryName();
                    if (fromHome) {
                        path += "/to/" + to + "/";
                    } else if (fromOthers) {
                        path += "/from/" + from + "/";
                    } else {
                        path += "/uncategorized";
                    }
                } else {
                    path += "/uncategorized";
                }
            } else {
                path = "uncategorized";
            }
            System.out.println(path + "XXXXXXXXXXX");
            //fileSystemService.storeFile(file, path, userDao.show(username).getId(),contentResult);
            responseDto.setMessage("file_upload_success");
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
        if (!docStructureClassification.trainTagger(path, request.getTagType(), request.getDisplayName(), request.getData())) {
            response.setMessage("problem_in_saving");
        } else {
            response.setMessage("training_successfully_saved");
            fileSystemService.createFolderFromCategory(request.getDisplayName(), userDao.show(username).getId());
        }
        return response;
    }

    public GeneralMessageResponseDto sampleTrainContent() throws IOException {
        boolean ifError = contentClassificationService.train(1);
        GeneralMessageResponseDto response = new GeneralMessageResponseDto();
        if(!ifError){
            response.setMessage("Error");
        }else{
            response.setMessage("Success!");
        }
        return response;
    }

    @Override
    public void createCategory(String categoryName, String username) {
        Category cat = new Category();

        cat.setCategoryName(categoryName);
        System.out.println(userDao.show(username) + " is the username");
        cat.setUser(userDao.show(username));
        boolean hasCreated = categoryDao.create(cat);
        if(hasCreated){
            System.out.println("no error");
        }
        else{
            System.out.println("error");
        }
    }

    @Override
    public GeneralMessageResponseDto createCategorySample() {
        GeneralMessageResponseDto response = new GeneralMessageResponseDto();
        Category cat = new Category();

        cat.setCategoryName("Excuse");
        cat.setUser(userDao.show("holyjohn00"));
        boolean hasCreated = categoryDao.create(cat);
        if(hasCreated){
            response.setMessage("no error");
        }
        else{
            response.setMessage("error");
        }
        return response;
    }

    @Override
    public GeneralMessageResponseDto contentTrain(String username, MultipartFile[] file, String categoryName) throws IOException {
        Category cat = new Category();
        System.out.println(file.length);
        cat.setCategoryName(categoryName);
        cat.setUser(userDao.show(username));
        cat.setNumberOfFiles(file.length);
        categoryDao.create(cat);

        contentClassificationService.writeToFile(file, environment.getProperty("storage.users") + username + "/" + environment.getProperty("storage.content_training") + "/" + categoryName);


        GeneralMessageResponseDto response = new GeneralMessageResponseDto();

        boolean noError = contentClassificationService.train(userDao.show(username).getId());

        if (noError) {
            response.setMessage("training_successfully_saved");
        } else {
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

        if (fileSystemService.storeFileNotMapped(file, environment.getProperty("storage.content_training") + "/categoryName", userDao.show(username).getId())) {
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
            ImageIO.setUseCache(false);
            ImageIO.read(mfile.getInputStream()).toString();
            text = aspriseOCRService.doOCR(multipartToFile(mfile));
        } catch (IOException e) {
        }
        return text;
    }

    private List<String> getLinesContent(MultipartFile mfile) {
        List<String> text = null;
        String extension = FilenameUtils.getExtension(mfile.getOriginalFilename());
        if (extension.equals(".docx")) {
            text = docxService.readDocx(multipartToFile(mfile));
        } else try {
            ImageIO.setUseCache(false);
            ImageIO.read(mfile.getInputStream()).toString();
            text = aspriseOCRService.doOCRContent(multipartToFile(mfile));
        } catch (IOException e) {
        }
        return text;
    }

    private String getYear(String line) {
        Pattern pattern = Pattern.compile("\\d{4}");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(0);
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
