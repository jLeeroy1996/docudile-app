/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.docudile.app.services.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author franc
 */
public class FileHandler {

    public static boolean writeToFile(List<String> lines, String filename) {
        try {
            System.out.println("i was here.");
            new File(filename).getParentFile().mkdirs();
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);

            for (String line : lines) {
                bw.append(line);
                bw.newLine();
            }
            bw.flush();
            bw.close();
            fw.close();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return false;
    }

    public static boolean writeToFile(Map<String, List<String>> filesToWrite, String path) {
        try {
            new File(path).getParentFile().mkdirs();
            for (String filename : filesToWrite.keySet()) {
                FileWriter fw = new FileWriter(path + "/" + filename);
                BufferedWriter bw = new BufferedWriter(fw);
                for (String line : filesToWrite.get(filename)) {
                    bw.append(line);
                    bw.newLine();
                }
                bw.flush();
                bw.close();
                fw.close();
            }
            return true;
        } catch (Exception ex) {
        }
        return false;
    }

    public static List<String> readFile(String path) {
        List<String> lines = new ArrayList<>();
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);

            String temp;
            while ((temp = br.readLine()) != null) {
                lines.add(temp);
            }
        } catch (Exception ex) {
        }
        return lines;
    }
    
    public static Map<String, List<String>> readAllFiles(String folder) throws IOException {
        ArrayList<String> filePaths = getFilePaths(folder);
        Map<String, List<String>> files = new HashMap<>();
        
        for (String filePath : filePaths) {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<String> lines = new ArrayList<String>();
            String temp;

            while ((temp = br.readLine()) != null) {
                if (StringUtils.isNotEmpty(temp)) {
                    lines.add(temp);
                }
            }
            files.put(filePath, lines);
            br.close();
            fr.close();
        }
        System.out.println(files.size());
        return files;
    }
    
    private static ArrayList<String> getFilePaths(String folder) {
        ArrayList<String> filePaths = new ArrayList<>();
        File file = new File(folder);

        for (File filename : file.listFiles()) {
            if (filename.isFile()) {
                filePaths.add(filename.getAbsolutePath());
            }
        }
        return filePaths;
    }

}
