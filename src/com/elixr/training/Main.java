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
            System.out.println("Argument missing"); //changed error message file path not valid to argument missing
            return;
        }
        String inputfilepath = args[0]; //changed variable name from inputfile to inputfilepath
        String searchword = args[1];  //changed variable name from wordsearch to searchword
        File file = new File(inputfilepath);
        System.out.println("Processing................");
        String data = readFile(inputfilepath); //combined two lines
        isSupportedFile(file);
        searchword(data, searchword);

    }

    public static String readFile(String fileName) {
        String data = null; //changes "" to null
        try {
            data = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void isSupportedFile(File file) {      //changed from supportfile method to isSupportedFile and
        // changed return type from boolean to void
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



