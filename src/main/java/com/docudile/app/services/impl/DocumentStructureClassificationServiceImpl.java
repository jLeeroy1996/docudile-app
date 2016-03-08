package com.docudile.app.services.impl;

import com.docudile.app.services.DocumentStructureClassificationService;
import com.docudile.app.services.TfIdfService;
import com.docudile.app.services.utils.FileHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by FrancAnthony on 3/2/2016.
 */
@Service("documentStructureClassificationService")
@Transactional
@PropertySource({"classpath:/storage.properties"})
public class DocumentStructureClassificationServiceImpl implements DocumentStructureClassificationService {

    @Autowired
    private TfIdfService tfIdfService;

    @Autowired
    private Environment environment;

    @Override
    public Map<Integer, String> tag(String path, List<String> lines) {
        Map<Integer, String> tagged = new HashMap<>();
        Map<String, List<String>> tags = getTags(environment.getProperty("storage.base_tags"));
        Map<String, Map<String, List<String>>> fallbackTags = getFallbackTags(path);
        int i = 0;
        for (String line : lines) {
            System.out.println(line);
            boolean found = false;
            for (String tag : tags.keySet()) {
                for (String rule : tags.get(tag)) {
                    Pattern pattern = Pattern.compile(rule);
                    if (pattern.matcher(line).matches()) {
                        found = true;
                        tagged.put(i, tag);
                        break;
                    }
                }
            }
            if (!found) {
                String tag = tagWithFallback(line, fallbackTags);
                if (StringUtils.isNotEmpty(tag)) {
                    found = true;
                    tagged.put(i, tag);
                }
            }
            if (found) {
                if (tagged.get(i).equals("SALUTATION") || tagged.get(i).equals("SUBJECT")) {
                    break;
                }
            }
            i++;
        }
        return tagged;
    }

    @Override
    public String classify(String query, String dataPath) {
        return tfIdfService.search(query, dataPath);
    }

    @Override
    public boolean trainTagger(String path, String tagType, String displayName, List<String> lines) {
        String temp = displayName + ":=";
        for (String line : lines) {
            temp += line + "|";
        }
        return writeToFile(temp, path + tagType + ".txt");
    }

    @Override
    public boolean trainClassifier(String path, List<String> tags, String type) {
        if (writeToFile(tags, path + type + ".txt")) {
            return tfIdfService.process(path, path + "processed");
        }
        return false;
    }

    @Override
    public boolean delete(String path) {
        return new File(path).delete();
    }

    private boolean writeToFile(String line, String path) {
        List<String> temp = new ArrayList<>();
        temp.add(line);
        return writeToFile(temp, path);
    }

    private String tagWithFallback(String line, Map<String, Map<String, List<String>>> fallbackTags) {
        for (String filename : fallbackTags.keySet()) {
            Map<String, List<String>> fileTags = fallbackTags.get(filename);
            for (String displayName : fileTags.keySet()) {
                for (String data : fileTags.get(displayName)) {
                    if (line.trim().equalsIgnoreCase(data)) {
                        return filename + "-" + displayName;
                    }
                }
            }
        }
        return null;
    }

    private boolean writeToFile(List<String> lines, String path) {
        File file = new File(path);
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String line : lines) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Map<String, List<String>> getTags(String location) {
        Map<String, List<String>> tags = new HashMap<>();
        try {
            FileReader fileReader = new FileReader(location);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                String[] tokens = temp.split(":=");
                if (tokens.length >= 2) {
                    if (tags.containsKey(tokens[0])) {
                        List<String> tempTags = tags.get(tokens[0]);
                        tempTags.add(tokens[1]);
                    } else {
                        List<String> tempTags = new ArrayList<>();
                        tempTags.add(tokens[1]);
                        tags.put(tokens[0], tempTags);
                    }
                }
            }
            return tags;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, Map<String, List<String>>> getFallbackTags(String folderPath) {
        Map<String, Map<String, List<String>>> fallbackTags = new HashMap<>();
        try {
            Map<String, List<String>> filenames = FileHandler.readAllFiles(folderPath);
            for (String filename : filenames.keySet()) {
                Map<String, List<String>> fileTags = new HashMap<>();
                for (String line : filenames.get(filename)) {
                    String[] temp = line.split(":=");
                    String displayName = temp[0];
                    List<String> datas = new ArrayList<>();
                    for (String tempData : temp[1].split("|")) {
                        datas.add(tempData);
                    }
                    fileTags.put(displayName, datas);
                }
                fallbackTags.put(filename, fileTags);
            }
            return fallbackTags;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
