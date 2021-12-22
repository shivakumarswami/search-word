package com.elixr.training;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import java.lang.String;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Argument missing");
            return;
        }
        String inputfilepath = args[0];
        String searchword = args[1];
        File file = new File(inputfilepath);
        System.out.println("Processing................");
        String data = readFile(inputfilepath);
        isSupportedFile(file);
        searchword(data, searchword);

    }

    public static String readFile(String fileName) {
        String data = null;
        try {
            data = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void isSupportedFile(File file) {
        if (file.getName().endsWith(".txt") && (file.length() != 0)) {
            System.out.println("File format supported");
        } else {
            System.out.println("File format not supported");
            System.exit(0);
        }

    }

    public static void searchword(String data, String searchword) {
        StringTokenizer st = new StringTokenizer(data);
        int count = 0;
        while (st.hasMoreTokens()) {
            if (searchword.equalsIgnoreCase(st.nextToken())) {
                count++;
            }
        }
        if (count == 0) {
            System.out.println("Word not found");
        } else {
            System.out.println("The word has been found");
            System.out.println("The word has been repeated for " + count + " times");
        }
    }
}



