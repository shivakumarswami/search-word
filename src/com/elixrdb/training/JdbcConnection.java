package com.elixrdb.training;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcConnection {
    private static final String url = "jdbc:mysql://localhost:3306/mysqlsearchword";
    private static final String user = "root";
    private static final String password = "tiger";
    private static final String sqlQuery = "INSERT INTO audit VALUES (?,?,now(),?,?,?)";
    private static Connection con;
    private static PreparedStatement stmt;


    public static void dbOperation(String inputFilePath, String searchWord, String STATUS_FAILURE, int count, String ERROR_MESSAGE) throws SQLException {

        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.prepareStatement(sqlQuery);
            stmt.setString(1, inputFilePath);
            stmt.setString(2, searchWord);
            stmt.setString(3, STATUS_FAILURE);
            stmt.setInt(4, count);
            stmt.setString(5, ERROR_MESSAGE);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }
}

