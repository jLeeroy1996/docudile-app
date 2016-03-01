/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.docudile.app.services.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author franc
 */
public class StringHandler {
    
    private static final String fileLoc = "data/stopwords.txt";
    
    public static boolean isStopWord(String word) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(fileLoc);
        BufferedReader br = new BufferedReader(fr);
        
        String temp;
        while ((temp = br.readLine()) != null) {
            if (word.trim().equalsIgnoreCase(temp)) {
                return true;
            }
        }
        return false;
    }
    
    public static List<String> tokenizer(String line, String delim) {
        StringTokenizer stkn = new StringTokenizer(line, delim);
        List<String> tokens = new ArrayList<>();

        while (stkn.hasMoreTokens()) {
            tokens.add(stkn.nextToken());
        }
        return tokens;
    }
    
    public static boolean cleanWord(String word) throws IOException {
        return !isStopWord(word) && word.matches("^[a-zA-Z0-9]*$") && word.length() > 1;
    }
    
}
