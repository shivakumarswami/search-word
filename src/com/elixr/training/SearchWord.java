package com.elixr.training;

import com.elixrdb.training.JdbcConnection;

import static com.elixr.training.Main.*;

import java.sql.SQLException;
import java.util.StringTokenizer;

public class SearchWord extends Thread {
    String data;
    int count;
    JdbcConnection database;

    public SearchWord(String data, int count, JdbcConnection database) {
        this.data = data;
        this.count = count;
        this.database = database;
    }

    public void run() {
        StringTokenizer st = new StringTokenizer(data);

        while (st.hasMoreTokens()) {
            if (searchWord.equalsIgnoreCase(st.nextToken())) {
                count++;
            }
        }
        if (count == 0) {
            System.out.println(Constants.ERROR_MESSAGE_WORD_NOT_FOUND);
            try {
                database.dbOperation(inputFilePath, searchWord, Constants.STATUS_FAILURE, count, Constants.ERROR_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        } else {
            System.out.println(Constants.ERROR_MESSAGE_WORD_HAS_BEEN_FOUND + count + " times");
            try {
                database.dbOperation(inputFilePath, searchWord, "Success", count, "null");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
    }
}
