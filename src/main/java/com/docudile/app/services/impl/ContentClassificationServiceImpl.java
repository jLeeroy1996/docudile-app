package com.docudile.app.services.impl;

import com.docudile.app.data.dao.*;
import com.docudile.app.data.dto.CategoryDto;
import com.docudile.app.data.dto.FileContentDto;
import com.docudile.app.data.dto.WordListDto;
import com.docudile.app.data.entities.*;
import com.docudile.app.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by cicct on 2/15/2016.
 */
@Service("contentClassificationService")
@Transactional
@PropertySource({"classpath:/storage.properties"})
public class ContentClassificationServiceImpl implements ContentClassificationService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private Environment environment;

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

    @Autowired
    private AspriseOCRService aspriseOCRService;

    @Autowired
    private WordListCategoryDao wordListCategoryDao;

    @Autowired
    private WordListDocumentDao wordListDocumentDao;

    @Autowired
    private StemmerService stemmerService;

    public boolean train(Integer userID) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        WordListDto wordList = new WordListDto();
        List<FileContentDto> fileDto = new ArrayList<>();
        List<com.docudile.app.data.entities.File> file = new ArrayList<>();
        CategoryDto category = new CategoryDto();
        FileContentDto fileContentDto = new FileContentDto();
        List<CategoryDto> categoriesDto = new ArrayList<>();

        //get Categories from DB
        List<Category> categories = categoryDao.getCategories(userID);
        System.out.println(categories.size() + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        for (int x = 0; x < categories.size(); x++) {
            //instantiate a Category Class ( new Category(categoryName,fileCount) )
            category = new CategoryDto();
            System.out.println(categories.get(x).getCategoryName() + " EEEEEEEEE");
            category.setName(categories.get(x).getCategoryName());
            category.setCategoryID(categories.get(x).getId());
            //add it into List<Category> categories
            categoriesDto.add(category);
        }

        User user = userDao.getUserDetails(userID);

        for (int x = 0; x < categoriesDto.size(); x++) {
            System.out.println(categoriesDto.get(x).getName() + " @@@@@@@@@@@@@@@@@@@ ");
            java.io.File folder = new java.io.File(environment.getProperty("storage.users") + userDao.show(userID).getUsername() + "//" + environment.getProperty("storage.content_training") + "//" + categoriesDto.get(x).getName());
            for (final java.io.File fileEntry : folder.listFiles()) {
                fileContentDto = new FileContentDto();
                fileContentDto.setFileName(fileEntry.getName());
                fileContentDto.setCategoryName(categoriesDto.get(x).getName());
                String extension = FilenameUtils.getExtension(fileEntry.getAbsoluteFile().toString());
                System.out.println(extension + " sssssssssss");
                if (extension.equals("docx")) {
                    fileContentDto.setWordList(docxService.readDocxContent(fileEntry));
                } else {
                    ImageIO.read(fileEntry.getAbsoluteFile()).toString();
                    fileContentDto.setWordList(aspriseOCRService.doOCRContent(fileEntry));
                    System.out.println(fileContentDto.getWordList());
                }

                //String[] temporary = FilenameUtils.getBaseName(fileEntry.getAbsoluteFile().toString()).split(" ");
                //List<String> temp = fileContentDto.getWordList();
                //temp.addAll(Arrays.asList(temporary));
                fileContentDto.setWordList(checkWords(fileContentDto.getWordList()));
                fileDto.add(fileContentDto);
            }
        }

//        category.setName("SampleCategory");
//        category.setFileCount(2);
//        category.setCategoryID(1);
//        categoriesDto.add(category);

        //User user = userDao.getUserDetails(userID);

//        for(int x = 0;x<categoriesDto.size();x++) {
//            //java.io.File folder = new java.io.File(environment.getProperty("storage.users") + userDao.show(userID).getUsername() + "/" + environment.getProperty("storage.content_training") + "/" + categoriesDto.get(x).getName());
//            java.io.File folder = new java.io.File("C:\\Docudile\\TestingFiles");
//            for (final java.io.File fileEntry : folder.listFiles()) {
//                System.out.println(fileEntry);
//                FileContentDto fileContentDto = new FileContentDto();
//                fileContentDto.setFileName(fileEntry.getName());
//                fileContentDto.setCa  tegoryName(categoriesDto.get(x).getName());
//                fileContentDto.setWordList(docxService.readDocx(fileEntry));
//                fileDto.add(fileContentDto);
//            }
//        }

        //get wordList in DB

        wordList.setWordList(wordListDao.getWords());
        wordList.setCount(wordListDao.getWords().size());


        //get updated wordList
        wordList = getDistinctWords(fileDto, wordList);
        //end

        //count words
        categoriesDto = countWords(fileDto, wordList, categoriesDto);
        //end
        // get vectors
        float[][] wordListVectors = calculateNaiveBayes(wordList, categoriesDto);
        //end


        //put to DB si wordListVectors
        for (int x = 0; x < wordListVectors.length; x++) {
            for (int y = 0; y < wordListVectors[0].length; y++) {
                WordListCategory wordListCategory = new WordListCategory();

                if (wordListCategoryDao.isExist(categoriesDto.get(y).getCategoryID(), wordListDao.getID(wordList.getWordList().get(x)).getWord())) {
                    wordListCategory = wordListCategoryDao.getVector(categoriesDto.get(y).getCategoryID(), wordListDao.getID(wordList.getWordList().get(x)).getWord());
                    wordListCategory.setCount(wordListVectors[x][y]);
                    wordListCategoryDao.update(wordListCategory);
                } else {
                    wordListCategory.setWordList(wordListDao.getID(wordList.getWordList().get(x)));
                    wordListCategory.setCategory(categoryDao.getCategory(categoriesDto.get(y).getCategoryID()));
                    wordListCategory.setCount(wordListVectors[x][y]);
                    wordListCategoryDao.create(wordListCategory);
                }
            }
        }
        return true;
    }


    public WordListDto getDistinctWords(List<FileContentDto> files, WordListDto list) {
        boolean isExist = false;
        List<String> wordListWords = list.getWordList();
        for (int x = 0; x < files.size(); x++) {
            if (files.get(x).getWordList() == null) {
                continue;
            }
            List<String> words = files.get(x).getWordList();
            if (words == null) {
                continue;
            }
            for (int y = 0; y < words.size(); y++) {
                if (isStopWord(words.get(y))) {
                    continue;
                }
                for (int z = 0; z < wordListWords.size(); z++) {
                    if ((words.get(y).equalsIgnoreCase(wordListWords.get(z)))) {
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    WordList word = new WordList();
                    wordListWords.add(words.get(y).toLowerCase());
                    word.setWord(words.get(y).toLowerCase());
                    wordListDao.create(word);
                }
                isExist = false;
            }
        }
        WordListDto wordList = new WordListDto(wordListWords);
        return wordList;
    }

    public List<CategoryDto> countWords(List<FileContentDto> files, WordListDto wordList, List<CategoryDto> categories) {

        String[][] countWords = new String[wordList.getWordList().size()][2];
        List<String> wordListWords = wordList.getWordList();
        List<String> filesWordList;
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

    public Integer categorize(List<String> words, Integer userID, String filename) {
        List<CategoryDto> categories = new ArrayList<>();
        //get data from DB Category
        List<String> finalWords = new ArrayList<>();
        List<Category> categoryList = categoryDao.getCategories(userID);
        for(int x = 0;x<words.size();x++){
            StringTokenizer st = new StringTokenizer(words.get(x)," ~`!@#$%^&*()-=_+[]{};'\\\\:|,./<>?");
            while(st.hasMoreTokens()){
                while(st.nextToken().length()>1) {
                    finalWords.add(st.nextToken());
                }
            }
        }
        words = finalWords;


        CategoryDto categoryDto = new CategoryDto();
        for (int x = 0; x < categoryList.size(); x++) {
            categoryDto.setName(categoryList.get(x).getCategoryName());
            categoryDto.setFileCount(categoryList.get(x).getNumberOfFiles());
            categoryDto.setCategoryID(categoryList.get(x).getId());
            categories.add(categoryDto);
        }
        //instantiate a Category Class ( new Category(categoryName,fileCount) )
        //add Categories in List<Category> categories
        int totalFiles = 0;

        WordListDto wordList = new WordListDto();
        wordList.setWordList(wordListDao.getWords());
        wordList.setCount(wordListDao.getWords().size());

        FileContentDto fileContentDto = new FileContentDto();
        fileContentDto.setWordList(words);
        fileContentDto.setFileName(filename);
        List<FileContentDto> fileList = new ArrayList<>();
        fileList.add(fileContentDto);
        wordList = getDistinctWords(fileList, wordList);

        float[] categoryVectors = new float[categoryList.size()];

        for (int x = 0; x < categories.size(); x++) {
            totalFiles += categories.get(x).getFileCount();
        }

        for (int x = 0; x < categories.size(); x++) {
            categoryVectors[x] = categories.get(x).getFileCount() / totalFiles;
        }

        for (int x = 0; x < words.size(); x++) {
            for (int y = 0; y < categoryVectors.length; y++) {
                float temp = 1;
                if (wordListCategoryDao.getVector(categoryList.get(y).getId(), words.get(x)) != null) {
                    temp = wordListCategoryDao.getVector(categoryList.get(y).getId(), words.get(x)).getCount();
                }
                categoryVectors[y] *= temp;

            }
        }


        categoryDto = categories.get(0);
        float temp = categoryVectors[0];
        for (int x = 0; x < categoryVectors.length; x++) {
            if (categoryVectors[x] < temp) {
                categoryDto = categories.get(x);
                temp = categoryVectors[x];
            }
        }
        com.docudile.app.data.entities.File f = new com.docudile.app.data.entities.File();
        f.setFilename(filename);
        f.setUser(userDao.show(userID));
        fileDao.create(f);
        getCount(fileList, wordList, fileDao.getFileID(filename, userID).getId());

        return categoryDto.getCategoryID();
    }


    public String readDocxFile(String fileName) {
        String sentence = "";
        try {
            java.io.File file = new java.io.File(fileName);
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

    private void getCount(List<FileContentDto> files, WordListDto wordList, Integer fileID) {
        String[][] wordCount = new String[wordList.getWordList().size()][2];
        WordListDocument wordListDocument = new WordListDocument();


        for (int x = 0; x < wordList.getWordList().size(); x++) {
            wordCount[x][0] = wordList.getWordList().get(x);
            wordCount[x][1] = "0";

        }
        for (int x = 0; x < files.get(0).getWordList().size(); x++) {
            for (int y = 0; y < wordCount.length; y++) {
                if (wordCount[y][0].equalsIgnoreCase(files.get(0).getWordList().get(x))) {
                    wordCount[y][1] = ((Integer.parseInt(wordCount[y][1]) + 1)) + "";
                }

            }
        }
        for (int x = 0; x < wordCount.length; x++) {
            wordListDocument = new WordListDocument();
            wordListDocument.setWordList(wordListDao.show(wordListDao.getID(wordCount[x][0]).getId()));
            wordListDocument.setFile(fileDao.show(fileID));
            wordListDocument.setVectorCount(Integer.parseInt(wordCount[x][1]));
            wordListDocumentDao.create(wordListDocument);
        }

    }

    public void writeToFile(MultipartFile[] f, String path) throws IOException {

        System.out.println(f.toString() + " ###########");
        for (int x = 0; x < f.length; x++) {
            File file = new File(path + "//" + f[x].getOriginalFilename());
            file.getParentFile().mkdirs();
            f[x].transferTo(file);
        }

    }

    public void createCategory(String categoryName, Integer userID) {
        Category cat = new Category();
        cat.setCategoryName(categoryName);
        cat.setUser(userDao.getUserDetails(userID));
        cat.setId(1);
        categoryDao.create(cat);
        System.out.println(categoryName + "created Category");
    }

    public boolean isStopWord(String word) {
        String[] stopwords = {"a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero"};
        List<String> stopWords = Arrays.asList(stopwords);
        if (stopWords.contains(word)) {
            return true;
        }
        return false;
    }

    public List<String> checkWords(List<String> wordList) {
        List<String> finalList = new ArrayList<>();
        for (int x = 0; x < wordList.size(); x++) {
            if (stemmerService.checkIfInDictionary(wordList.get(x).trim())) {
                finalList.add(wordList.get(x).toLowerCase().trim());
            }
        }
        return finalList;
    }


}
