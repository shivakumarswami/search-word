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
    static String searchWord;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println(Constants.ERROR_MESSAGE_ARGUMENTS_NOT_FOUND);
            try {
                database.dbOperation(inputFilePath, searchWord, Constants.STATUS_FAILURE, count, Constants.ERROR_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        inputFilePath = args[0];
        searchWord = args[1];
        File file = new File(inputFilePath);

        System.out.println("Processing................");

        if (!file.exists()) {
            System.out.println(Constants.ERROR_MESSAGE_FILE_NOT_EXISTS);
            return;
        }

        if (!isSupportedFile(file)) {
            System.out.println(Constants.ERROR_MESSAGE_FILE_NOT_VALID);
            return;
        }
        String data = readFile(inputFilePath);

        if (data == null) {
            System.out.println(Constants.ERROR_MESSAGE_COULD_NOT_READ_DATA);
            return;
        }
        if (file.length() == 0) {
            System.out.println(Constants.ERROR_MESSAGE_DATA_NOT_FOUND);
            return;
        }
        try {
            SearchWord search = new SearchWord(data, count, database);
            search.start();
            search.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            System.out.println(Constants.ERROR_MESSAGE_FILE_FORMAT_SUPPORTED);
            return true;
        } else {
            System.out.println(Constants.ERROR_MESSAGE_FILE_FORMAT_NOT_SUPPORTED);
            return false;
        }
    }

}



