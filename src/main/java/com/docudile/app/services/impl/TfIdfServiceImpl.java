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
    public boolean process(String dataPath, String savePath) {
        try {
            System.out.println(dataPath);
            Map<String, List<String>> fileTokens = getFileTokens(FileHandler.readAllFiles(dataPath));
            Set<String> distinctTokens = getDistinctTokens(fileTokens);
            Map<String, Integer[]> fileTokenCounts = getFileTokenCounts(fileTokens, distinctTokens);
            Double[] idfWeights = getIdfWeight(fileTokenCounts, distinctTokens.size());
            List<Double[]> normalization = getNormalization(fileTokenCounts, idfWeights);
            List<Double> docuLengths = getDocuLength(normalization);

            ArrayList<String> lines = new ArrayList<>();
            for (String distinctToken : distinctTokens) {
                lines.add(distinctToken);
            }
            FileHandler.writeToFile(lines, savePath + "/tokens.dat");
            lines = new ArrayList<>();
            for (String key : fileTokens.keySet()) {
                lines.add(key);
            }
            FileHandler.writeToFile(lines, savePath + "/tags.dat");
            lines = new ArrayList<>();
            for (Double idfWeight : idfWeights) {
                lines.add(String.valueOf(idfWeight));
            }
            FileHandler.writeToFile(lines, savePath + "/idfWeights.dat");
            lines = new ArrayList<>();
            for (Double[] normalizedValues : normalization) {
                String temp = "";
                for (Double normalizationValue : normalizedValues) {
                    temp += normalizationValue + " ";
                }
                lines.add(temp);
            }
            FileHandler.writeToFile(lines, savePath + "/normalization.dat");
            lines = new ArrayList<>();
            for (Double docuLength : docuLengths) {
                lines.add(String.valueOf(docuLength));
            }
            FileHandler.writeToFile(lines, savePath + "/docuLength.dat");
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return false;
    }

    @Override
    public String search(String query, String dataPath) {
        try {
            List<String> tokens = loadTokens(dataPath);
            List<String> titles = loadTitles(dataPath);
            List<Double> idfWeights = loadIdfWeights(dataPath);
            List<List<Double>> normalization = loadNormalization(dataPath);
            List<Double> docuLength = loadDocuLengths(dataPath);

            List<Double> queryTfIdf = getTfIdf(getQueryCounts(query, tokens), idfWeights);
            List<Double> vSearch = getVSearch(normalization, queryTfIdf);
            Double queryLength = getQueryLength(queryTfIdf);
            List<Double> result = getDegreeValues(getSimilarityValues(vSearch, queryLength, docuLength));
            return getSearchResult(titles, result);
        } catch (Exception ex) {
        }
        return null;
    }

    private Map<String, List<String>> getFileTokens(Map<String, List<String>> fileLines) throws IOException {
        Map<String, List<String>> fileTokens = new HashMap<>();

        for (String path : fileLines.keySet()) {
            ArrayList<String> tokens = new ArrayList<>();
            for (String line : fileLines.get(path)) {
                tokens.addAll(StringHandler.tokenizer(line.toLowerCase(), Defaults.DELIMETER));
            }
            fileTokens.put(path, tokens);
        }
        return fileTokens;
    }

    private Set<String> getDistinctTokens(Map<String, List<String>> fileTokens) {
        Set<String> distinctTokens = new TreeSet<>();

        for (String key : fileTokens.keySet()) {
            distinctTokens.addAll(fileTokens.get(key));
        }
        return distinctTokens;
    }

    private Map<String, Integer[]> getFileTokenCounts(Map<String, List<String>> fileTokens, Set<String> distinctTokens) {
        Map<String, Integer[]> fileTokenCounts = new LinkedHashMap<>();
        for (String path : fileTokens.keySet()) {
            Integer[] counts = new Integer[distinctTokens.size()];
            Arrays.fill(counts, 0);
            int i = 0;
            for (String token : distinctTokens) {
                for (String articleToken : fileTokens.get(path)) {
                    if (articleToken.equals(token)) {
                        counts[i]++;
                    }
                }
                i++;
            }
            fileTokenCounts.put(path, counts);
        }
        return fileTokenCounts;
    }

    private Double[] getIdfWeight(Map<String, Integer[]> tokenCounts, int tokensCnt) {
        Double[] idfWeight = new Double[tokensCnt];
        for (int i = 0; i < tokensCnt; i++) {
            idfWeight[i] = MathCalculations.log2((double) tokenCounts.size() / (double) getWordDocuCount(i, tokenCounts));
        }
        return idfWeight;
    }

    private List<Double[]> getNormalization(Map<String, Integer[]> fileTokenCounts, Double[] idfWeight) {
        List<Double[]> normalization = new ArrayList<>();
        for (String key : fileTokenCounts.keySet()) {
            Double[] normalize = new Double[fileTokenCounts.get(key).length];
            for (int i = 0; i < fileTokenCounts.get(key).length; i++) {
                normalize[i] = idfWeight[i] * fileTokenCounts.get(key)[i];
            }
            normalization.add(normalize);
        }
        return normalization;
    }

    private List<Double> getDocuLength(List<Double[]> normalization) {
        List<Double> docuLength = new ArrayList<>();

        for (Double[] normalizedValues : normalization) {
            double length = 0;
            for (int i = 0; i < normalizedValues.length; i++) {
                length += Math.pow(normalizedValues[i], 2);
            }
            docuLength.add(Math.sqrt(length));
        }
        return docuLength;
    }

    private int getWordDocuCount(int index, Map<String, Integer[]> tokenCounts) {
        int count = 0;

        for (String key : tokenCounts.keySet()) {
            count += tokenCounts.get(key)[index];
        }
        return count;
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

        for (String token : StringHandler.tokenizer(query, Defaults.DELIMETER)) {
            String temp = token.toLowerCase();
            if (tokens.contains(temp)) {
                if (!queryTokens.containsKey(tokens.indexOf(temp))) {
                    queryTokens.put(tokens.indexOf(temp), 1);
                } else {
                    queryTokens.put(tokens.indexOf(temp), queryTokens.get(tokens.indexOf(temp)) + 1);
                }
            }
        }
        return queryTokens;
    }

    private List<Double> getTfIdf(Map<Integer, Integer> queryTokens, List<Double> idfWeight) {
        List<Double> tfIdf = new ArrayList<>();
        double max = MathCalculations.getMax(queryTokens);

        for (int key = 0; key < idfWeight.size(); key++) {
            if (queryTokens.containsKey(key)) {
                tfIdf.add(((double) queryTokens.get(key) / max) * idfWeight.get(key));
            } else {
                tfIdf.add(0.0);
            }
        }
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
        return tags.get(minIndex);
    }

}
