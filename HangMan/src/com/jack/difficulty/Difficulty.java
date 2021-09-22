package com.jack.difficulty;

import com.jack.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class Difficulty {

    private ArrayList words;

    public String generateWord() {
        words = new ArrayList();
        InputStream input = Main.class.getResourceAsStream("assets/dictionary.txt");
        InputStreamReader reader = new InputStreamReader(input);
        BufferedReader bReader = new BufferedReader(reader);
        String read = null;
        try {
            read = bReader.readLine();
            while(read != null) {
                words.add(read);
                read = bReader.readLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int maxWords = words.size();
        int randomNum = 0;
        String randomWord = "";
        while(!(randomWord.length() >= 5 && randomWord.length() <= 10)) {
            randomNum = (int) (Math.random() * maxWords);
            randomWord = (String) words.get(randomNum - 1);
        }

        return randomWord;
    }

    public abstract int getHealth();
}
