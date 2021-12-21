package com.elixr.training;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import java.lang.String;

public class Main {

    public static void main(String[] args) {

                if(args.length!=2)
                {
                    System.out.println("enter the valid path");
                    return;
                }
                String inputfile = args[0];
                String wordsearch = args[1];
                File file = new File(inputfile);
                supportfile(file);
                contains(file);
                System.out.println("processing................");
                String data = null;
                data = readFile(inputfile);
                wordsearch(data,wordsearch);
            }
            public static String readFile(String fileName)
            {
                String data = " ";
                try {
                    data = new String(Files.readAllBytes(Paths.get(fileName)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return data;
            }
            public static boolean supportfile(File file)
            {
                if (file.getName().endsWith(".txt")){
                    System.out.println("file format supported");
                }
                else{
                    System.out.println("file format not supported");
                }
                return false;
            }
            public static boolean contains(File file) {
                return (file.exists());
            }
            public static void wordsearch(String data,String wordsearch) {
                StringTokenizer st = new StringTokenizer(data);
                int count = 0;
                while (st.hasMoreTokens())
                {
                    if (wordsearch.equalsIgnoreCase(st.nextToken())) {
                        count++;
                    }
                }
                if (count == 0)
                {
                    System.out.println("Word not found");
                } else
                {
                    System.out.println("The word has been found");
                    System.out.println("The word has been repeated for " + count + " times");
                }
            }
        }



