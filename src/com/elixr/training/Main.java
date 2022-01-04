package com.elixr.training;



import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import java.lang.String;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;


public class Main {

    static final String TXTFILE = ".txt";
    static final String JSONFILE = ".json";


    public static void main(String[] args) throws SQLException {

        if (args.length != 2) {
            System.out.println(" File path and word to search are missing. Exiting the program... ");
            return;
        }
        String inputFilePath = args[0];
        String searchWord = args[1];
        File file = null;
        try {
            file = new File(inputFilePath);
        }catch (Exception e){
            e.printStackTrace();
        }



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
        searchWord(inputFilePath, data, searchWord);

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
        if (file.getName().endsWith(TXTFILE) || file.getName().endsWith(JSONFILE)) {
            System.out.println("File format supported");
            return true;
        } else {
            System.out.println("File format not supported");
            return false;
        }
    }

    public static void searchWord(String inputFilePath, String data, String searchWord) throws SQLException {
        StringTokenizer st = new StringTokenizer(data);
        int count = 0;
        while (st.hasMoreTokens()) {
            if (searchWord.equalsIgnoreCase(st.nextToken())) {
                count++;
            }
        }
        if (count == 0) {
            System.out.println("Word not found");
            JDBCconnection.SearchFailure(inputFilePath, searchWord, count);
        } else {
            System.out.println("The word has been found");
            System.out.println("The word has been repeated for " + count + " times");
            JDBCconnection.SearchSuccess(inputFilePath, searchWord, count);

        }
    }

    class JDBCconnection extends Main {

        private static String SqlQuery = "INSERT INTO audit VALUES (?,?,now(),?,?)";
        private static Connection Con = null;
        private static PreparedStatement stmt = null;

        public static void SearchSuccess(String inputFilePath, String searchWord, int count) throws SQLException {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysqlsearchword", "root", "tiger");
                stmt = Con.prepareStatement(SqlQuery);
                stmt.setString(1, inputFilePath);
                stmt.setString(2, searchWord);
                stmt.setString(3, "Success");
                stmt.setInt(4, count);
                stmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void SearchFailure(String inputFilePath, String searchWord, int count) throws SQLException {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysqlsearchword", "root", "tiger");
                stmt = Con.prepareStatement(SqlQuery);
                stmt.setString(1, inputFilePath);
                stmt.setString(2, searchWord);
                stmt.setString(3, "Failure");
                stmt.setInt(4, count);
                stmt.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}



