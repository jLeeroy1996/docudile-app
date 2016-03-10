package com.docudile.app.services.impl;

import com.docudile.app.services.TfIdfService;
import com.docudile.app.services.utils.Defaults;
import com.docudile.app.services.utils.FileHandler;
import com.docudile.app.services.utils.MathCalculations;
import com.docudile.app.services.utils.StringHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by FrancAnthony on 3/1/2016.
 */
@Service("tfIdfService")
@Transactional
public class TfIdfServiceImpl implements TfIdfService {

    @Override
    public String search(String query, String dataPath) {
        try {
            List<String> tokens = loadTokens(dataPath);
            System.out.println("lol1");
            List<String> titles = loadTitles(dataPath);
            System.out.println("lol2");
            List<Double> idfWeights = loadIdfWeights(dataPath);
            System.out.println("lol3");
            List<List<Double>> normalization = loadNormalization(dataPath);
            System.out.println("lol4");
            List<Double> docuLength = loadDocuLengths(dataPath);
            System.out.println("lol5");

            List<Double> queryTfIdf = getTfIdf(getQueryCounts(query, tokens), idfWeights);
            System.out.println("lol6");
            List<Double> vSearch = getVSearch(normalization, queryTfIdf);
            System.out.println("lol7");
            Double queryLength = getQueryLength(queryTfIdf);
            System.out.println("lol8");
            List<Double> result = getDegreeValues(getSimilarityValues(vSearch, queryLength, docuLength));
            System.out.println("lol9");
            return getSearchResult(titles, result);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    private List<String> loadTokens(String folderPath) {
        return FileHandler.readFile(folderPath + "/tokens.dat");
    }

    private List<String> loadTitles(String folderPath) {
        return FileHandler.readFile(folderPath + "/tags.dat");
    }

    private List<Double> loadIdfWeights(String folderPath) {
        List<Double> idfWeights = FileHandler.readFile(folderPath + "/idfWeights.dat").stream().map((Function<String, Double>) Double::parseDouble).collect(Collectors.toList());

        return idfWeights;
    }

    private List<List<Double>> loadNormalization(String folderPath) {
        List<List<Double>> fileNormalization = new ArrayList<>();
        List<String> fileLines = FileHandler.readFile(folderPath + "/normalization.dat");

        for (int i = 0; i < fileLines.size(); i++) {
            List<Double> normalization = new ArrayList<>();
            for (String count : fileLines.get(i).split(" ")) {
                normalization.add(Double.parseDouble(count));
            }
            fileNormalization.add(normalization);
        }
        return fileNormalization;
    }

    private List<Double> loadDocuLengths(String folderPath) {
        List<Double> idfWeights = FileHandler.readFile(folderPath + "/docuLength.dat").stream().map((Function<String, Double>) Double::parseDouble).collect(Collectors.toList());

        return idfWeights;
    }

    private Map<Integer, Integer> getQueryCounts(String query, List<String> tokens) throws IOException {
        Map<Integer, Integer> queryTokens = new LinkedHashMap<>();
        System.out.println(tokens);

        for (String token : StringHandler.tokenizer(query, " ")) {
            String temp;
            if (token.split("-").length > 0) {
                temp = token.split("-")[0];
            } else {
                temp = token;
            }
            temp = temp.toLowerCase();
            if (tokens.contains(temp)) {
                if (!queryTokens.containsKey(tokens.indexOf(temp))) {
                    queryTokens.put(tokens.indexOf(temp), 1);
                } else {
                    queryTokens.put(tokens.indexOf(temp), queryTokens.get(tokens.indexOf(temp)) + 1);
                }
            }
        }
        System.out.println("Query: " + queryTokens);
        return queryTokens;
    }

    private List<Double> getTfIdf(Map<Integer, Integer> queryTokens, List<Double> idfWeight) {
        List<Double> tfIdf = new ArrayList<>();
        double max = MathCalculations.getMax(queryTokens);
        System.out.println("herez");
        for (int key = 0; key < idfWeight.size(); key++) {
            if (queryTokens.containsKey(key)) {
                tfIdf.add(((double) queryTokens.get(key) / max) * idfWeight.get(key));
            } else {
                tfIdf.add(0.0);
            }
        }
        System.out.println("herez1");
        return tfIdf;
    }

    private List<Double> getVSearch(List<List<Double>> normalization, List<Double> tfIdf) {
        List<Double> vSearch = new ArrayList<>();

        for (List<Double> normalizationValues : normalization) {
            Double sum = 0.0;
            for (int i = 0; i < normalizationValues.size(); i++) {
                sum += normalizationValues.get(i) * tfIdf.get(i);
            }
            vSearch.add(sum);
        }
        return vSearch;
    }

    private Double getQueryLength(List<Double> tfIdf) {
        double length = 0;

        for (Double val : tfIdf) {
            length += Math.pow(val, 2);
        }
        return Math.sqrt(length);
    }

    private List<Double> getSimilarityValues(List<Double> vSearch, Double queryLength, List<Double> docuLength) {
        List<Double> similarityValues = new ArrayList<>();

        for (int i = 0; i < vSearch.size(); i++) {
            similarityValues.add(vSearch.get(i) / (queryLength * docuLength.get(i)));
        }
        return similarityValues;
    }

    private List<Double> getDegreeValues(List<Double> similarityValues) {
        List<Double> degreeValues = new ArrayList<>();

        for (Double similarityValue : similarityValues) {
            degreeValues.add(MathCalculations.getDegrees(MathCalculations.getACos(similarityValue)));
        }
        return degreeValues;
    }

    private String getSearchResult(List<String> tags, List<Double> degree) {
        int minIndex = 0;
        Double min = degree.get(minIndex);
        for (int i = 0; i < degree.size(); i++) {
            if (min > degree.get(i)) {
                minIndex = i;
                min = degree.get(minIndex);
            }
        }
        if (min < 90) {
            return tags.get(minIndex);
        } else {
            return null;
        }
    }

}
