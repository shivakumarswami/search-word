package com.elixr.training;
import static com.elixr.training.Main.*;
import java.sql.SQLException;
import java.util.StringTokenizer;

 public class SearchWord extends Thread {
    String data;
    int count;

    public SearchWord(String data, int count) {
        this.data = data;
        this.count = count;
    }

    public void run() {
        StringTokenizer st = new StringTokenizer(data);

        while (st.hasMoreTokens()) {
            if (searchWord.equalsIgnoreCase(st.nextToken())) {
                count++;
            }
        }
        if (count == 0) {
            System.out.println("Word not found");
            try {
                database.dbOperation(inputFilePath, searchWord, Constants.status, count);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        } else {
            System.out.println("The word has been found");
            System.out.println("The word has been repeated for " + count + " times");
            try {
                database.dbOperation(inputFilePath, searchWord, "Success", count);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
    }
}
