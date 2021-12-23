package com.elixr.training;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import java.lang.String;

public class Main {
    static  final String TXTFILE = ".txt" ;
    static final String JSONFILE = ".json";
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println(" File path and word to search are missing. Exiting the program... ");
            return;
        }
        String inputFilePath = args[0];
        String searchWord = args[1];
        File file = new File(inputFilePath);

        System.out.println("Processing................");

        if (!file.exists()) {
            System.out.println("file doesn't exists");
            return;
        }

        if(!isSupportedFile(file)) {
            System.out.println("File is not valid");
            return;
        }
        String data = readFile(inputFilePath);

        if(data==null) {
            System.out.println("could not read the data from file");
            return ;
        }
        if (file.length()==0) {
            System.out.println("Data not found");
            return;
        }
        searchWord(data, searchWord);
    }

    public static String readFile(String fileName) {
        String data = null;


        try {
            data = new String(Files.readAllBytes(Paths.get(fileName)));
            data = data.replaceAll("[^a-zA-Z0-9@]", " ");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static boolean isSupportedFile(File file) {
        if (file.getName().endsWith(TXTFILE) || file.getName().endsWith(JSONFILE) ) {
            System.out.println("File format supported");
            return true;
        } else {
            System.out.println("File format not supported");
            return false;
        }
    }
    public static void searchWord(String data, String searchword) {
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



