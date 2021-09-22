package com.jack;

import com.jack.difficulty.*;

import java.util.Scanner;

public class Utils {

    public Difficulty getDifficulty(){
        while(true) {

            System.out.println("Please pick a difficulty");
            System.out.println("1: Easy");
            System.out.println("2: Medium");
            System.out.println("3: Hard");
            System.out.println("4: Extremely Hard");
            System.out.print("Difficulty: ");

            Scanner scanner = new Scanner(System.in);

            try {
                int difficulty = scanner.nextInt();
                if (difficulty == 1) {
                    return new Easy();
                } else if (difficulty == 2) {
                    return new Medium();
                } else if (difficulty == 3) {
                    return new Hard();
                } else if (difficulty == 4) {
                    return new SuperHard();
                } else {
                    continue;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
    }
}
