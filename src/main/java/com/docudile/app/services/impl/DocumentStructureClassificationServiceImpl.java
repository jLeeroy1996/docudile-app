package com.docudile.app.services.impl;

import com.docudile.app.services.DocumentStructureClassificationService;
import com.docudile.app.services.TfIdfService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
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
public class DocumentStructureClassificationServiceImpl implements DocumentStructureClassificationService {

    @Autowired
    private TfIdfService tfIdfService;

    @Override
    public List<String> tag(String path, List<String> lines) {
        List<String> tagged = new ArrayList<>();
        Map<String, List<String>> tags = getTags(path);
        for (String line : lines) {
            boolean found = false;
            for (String tag : tags.keySet()) {
                for (String rule : tags.get(tag)) {
                    Pattern pattern = Pattern.compile(rule);
                    if (pattern.matcher(line).matches()) {
                        found = true;
                        tagged.add(tag);
                        break;
                    }
                }
            }
            if (!found) {
                String tag = tfIdfService.search(line, path + "processed");
                if (StringUtils.isNotEmpty(tag)) {
                    tagged.add(tag);
                }
            }
        }
        return tagged;
    }

    @Override
    public String classify(String query, String dataPath) {
        return tfIdfService.search(query, dataPath);
    }

    @Override
    public boolean trainTagger(String path, String tag, String line) {
        if (createAppendFile(line, path + tag + ".txt")) {
            return tfIdfService.process(path + tag + ".txt", path + "processed");
        }
        return false;
    }

    @Override
    public boolean trainClassifier(String path, List<String> tags, String type) {
        if (createAppendFile(tags, path + type + ".txt")) {
            return tfIdfService.process(path + type + ".txt", path + "processed");
        }
        return false;
    }

    private boolean createAppendFile(String line, String path) {
        List<String> lines = new ArrayList<>();
        lines.add(line);
        return createAppendFile(lines, path);
    }

    private boolean createAppendFile(List<String> lines, String path) {
        File file = new File(path);
        file.getParentFile().mkdirs();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            while((temp = bufferedReader.readLine()) != null) {
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

}
