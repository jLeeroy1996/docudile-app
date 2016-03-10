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
    public Map<Integer, String> tag(List<String> lines) {
        Map<Integer, String> tagged = new HashMap<>();
        Map<String, List<String>> tags = getTags(environment.getProperty("storage.base_tags"));
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
                if (found) {
                    break;
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
    public String classify(String query) {
        return tfIdfService.search(query, environment.getProperty("storage.classifier") + "/processed");
    }

    @Override
    public boolean delete(String path) {
        return new File(path).delete();
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

}
