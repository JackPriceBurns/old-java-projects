package com.jack;

import com.jack.difficulty.Difficulty;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private String word;
    private ArrayList originalWord;
    private ArrayList guessedWord;
    private ArrayList usedLetters;
    private int health;
    private boolean loop = true;

    public Game(Difficulty difficulty){
        if(difficulty != null) {
            word = difficulty.generateWord();
            originalWord = new ArrayList();
            guessedWord = new ArrayList();
            usedLetters = new ArrayList();
            for(int letterIndex = 0; letterIndex <= (word.length() - 1); letterIndex++) {
                originalWord.add(letterIndex, word.split("")[letterIndex]);
                guessedWord.add(letterIndex, "_");
            }
            health = difficulty.getHealth();
        } else {
            print("Error: Can not continue!");
        }

        print(word);

        while(loop) {
            if(health > 0){
                tick();
            } else {
                loop = false;
                print("You Failed! The word was: " + word + ". Better luck next time!");
            }
        }
    }

    private void tick() {
        print("----------------------------");
        for(int x = 0; x <= (guessedWord.size()-1); x++){
            print(guessedWord.get(x) + " ", false);
        }
        print("\n", false);
        print("Used letters: ", false);
        for(int x = 0; x <= (usedLetters.size()-1); x++){
            print(usedLetters.get(x) + " ", false);
        }
        print("\n", false);
        print("Please enter a letter: ", false);
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();
        if(answer.length() == 1){
            boolean hasLetter = false;
            for(int x = 0; x <= (usedLetters.size()-1); x++){
                if(answer.equalsIgnoreCase((String) usedLetters.get(x))){
                    hasLetter = true;
                }
            }

            if(hasLetter){
                print("That letter has already been used");
            } else {
                usedLetters.add(answer);
                boolean inWord = false;
                for(int x = 0; x <= (originalWord.size()-1); x++){
                    if(answer.equalsIgnoreCase((String) originalWord.get(x))){
                        guessedWord.set(x, originalWord.get(x));
                        inWord = true;
                    }
                }
                if(inWord){
                    print("That letter was in the word!");
                    boolean hasWon = true;
                    for(int x = 0; x <= (guessedWord.size()-1); x++){
                        if((((String) guessedWord.get(x)).equalsIgnoreCase((String) originalWord.get(x))) == false){
                            hasWon = false;
                        }
                    }
                    if(hasWon){
                        print("Congratulations! You won! The word was: " + word);
                        loop = false;
                    }
                } else {
                    health--;
                    print("That letter was not in the word!");
                    print("You have " + health + " tries left!");
                }
            }
        }
    }

    private void print(String str){
        System.out.println(str);
    }

    private void print(String str, boolean line){
        if(!line) {
            System.out.print(str);
        } else {
            System.out.println(str);
        }
    }
}
