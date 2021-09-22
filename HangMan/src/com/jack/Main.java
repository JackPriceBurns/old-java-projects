package com.jack;

public class Main {

    private Utils utils;
    
    public Main() {
        utils = new Utils();
        new Game(utils.getDifficulty());
    }

    public static void main(String[] args) {
        new Main();
    }
}