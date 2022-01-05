package com.elixrdb.training;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcConnection {
    private static String url = "jdbc:mysql://localhost:3306/mysqlsearchword";
    private static String user = "root";
    private static String password = "tiger";
    private static Connection con;
    private static PreparedStatement stmt;
    private static String sqlQuery = "INSERT INTO audit VALUES (?,?,now(),?,?)";

    public void dbOperation(String inputFilePath, String searchWord, String status, int count) throws SQLException {

        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.prepareStatement(sqlQuery);
            stmt.setString(1, inputFilePath);
            stmt.setString(2, searchWord);
            stmt.setString(3, status);
            stmt.setInt(4, count);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }
}

