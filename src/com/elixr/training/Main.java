package com.elixr.training;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.String;
import java.sql.*;
import com.elixrdb.training.JdbcConnection;

public class Main {
    static JdbcConnection database = new JdbcConnection();
    static int count = 0;
    static String inputFilePath;
    static String searchWord ;

    public static void main(String[] args) throws SQLException, InterruptedException {
        if (args.length != 2) {
            System.out.println(" File path and word to search are missing. Exiting the program... ");
            database.dbOperation(inputFilePath, searchWord,Constants.status, count);
            return;
        }
        inputFilePath = args[0];
        searchWord = args[1];
        File file = new File(inputFilePath);

        System.out.println("Processing................");

        if (!file.exists()) {
            System.out.println("file doesn't exists");
            return;
        }

        if (!isSupportedFile(file)) {
            System.out.println("File is not valid");
            return;
        }
        String data = readFile(inputFilePath);

        if (data == null) {
            System.out.println("Could not read the data from file");
            return;
        }
        if (file.length() == 0) {
            System.out.println("Data not found");
            return;
        }
        SearchWord search = new SearchWord(data, count);
        search.start();
        search.join();

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
        if (file.getName().endsWith(Constants.TXTFILE) || file.getName().endsWith(Constants.JSONFILE)) {
            System.out.println("File format supported");
            return true;
        } else {
            System.out.println("File format not supported");
            return false;
        }
    }
}



