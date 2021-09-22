package com.tinkeredweb.hashing;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;

public class Main {

    private String salt;

    public Main(String salt){
        this.salt = salt;
    }

    public String hashString(String string) throws UnsupportedEncodingException {
        boolean[] multiplier = binMultiplication(strToBin(string), intToBin(67));
        boolean[] binary = concat(binMultiplication(strToBin(string), strToBin(new StringBuilder(string).reverse().toString())), strToBin(salt));
        boolean[] hash = binMultiplication(binary, multiplier);
        byte[] bytes = binToBytes(hash);
        return new String(bytes, "UTF-8");
    }

    private byte[] binToBytes(boolean[] binary){
        int bytes = (int) ((double) binary.length / (double) 8) + 1;
        byte[] allBytes = new byte[bytes];
        int currentBit = 0;
        for (int x = 0; x < bytes; x++) {
            int currentBitWorth = (int) Math.pow(2, 7);
            int byteNum = 0;
            for(int y = 0; y < 8; y++) {
                if(currentBit > binary.length - 1) break;
                if(binary[currentBit]){
                    byteNum = byteNum + currentBitWorth;
                    currentBit++;
                } else {
                    currentBit++;
                }
                currentBitWorth = currentBitWorth / 2;
            }
            if(byteNum < 33){
                byteNum = byteNum * 3;
            } else if(byteNum > 126){
                byteNum = byteNum / 3;
            }
            allBytes[x] = (byte) byteNum;
        }
        return allBytes;
    }

    private boolean[] intToBin(int num){
        double number = (double) num;
        int bitsNeeded = log2(num) + 1;
        boolean[] binary = new boolean[bitsNeeded];

        double currentBit = Math.pow(2, bitsNeeded - 1);

        for(int x = 0; x < bitsNeeded; x++){
            if(number >= currentBit){
                binary[x] = true;
                number = number - currentBit;
            } else {
                binary[x] = false;
            }
            currentBit = currentBit / (double) 2;
        }

        return binary;
    }
    private boolean[] binMultiplication(boolean[] a, boolean[] b){
        HashSet<boolean[]> add = new HashSet<>();
        int x = 0;
        for(boolean bit : a){
            if(bit){
                boolean[] toAdd = concat(b, new boolean[a.length - x - 1]);
                for(int y = 0; y < a.length - x - 1; y++){
                    toAdd[b.length + y] = false;
                }
                add.add(toAdd);
            }
            x++;
        }

        return binAdd(add);
    }

    private boolean[] binAdd(HashSet<boolean[]> add) {
        int highestBits = 0;
        for (boolean[] b: add) {
            if(b.length > highestBits){
                highestBits = b.length;
            }
        }
        HashSet<boolean[]> adding = positionFix(add, highestBits);
        boolean[] finalAnswer = new boolean[0];
        int carry = 0;
        for(int x = highestBits; x > 0; x--) {
            int trueBits = carry;
            for (boolean[] binary : adding) {
                if(binary[x-1]){
                    trueBits++;
                }
            }
            double newCarry = (double) trueBits / 2;
            boolean[] answer = new boolean[1];
            answer[0] = newCarry - (int) newCarry > 0;
            finalAnswer = concat(answer, finalAnswer);
            carry = (int) newCarry;
        }
        return finalAnswer;
    }

    private HashSet<boolean[]> positionFix(HashSet<boolean[]> fixMe, int highestBits) {
        HashSet<boolean[]> fixedBinary = new HashSet<>();
        for(boolean[] binary : fixMe){
            if(binary.length < highestBits){
                int difference = highestBits - binary.length;
                boolean[] diffBinary = new boolean[difference];
                fixedBinary.add(concat(diffBinary, binary));
            } else {
                fixedBinary.add(binary);
            }
        }
        return fixedBinary;
    }

    public boolean[] concat(boolean[] a, boolean[] b) {
        int aLen = a.length;
        int bLen = b.length;
        boolean[] c= new boolean[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    private int log2(int bits) {
        int log = 0;
        if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
        if( bits >= 256 ) { bits >>>= 8; log += 8; }
        if( bits >= 16  ) { bits >>>= 4; log += 4; }
        if( bits >= 4   ) { bits >>>= 2; log += 2; }
        return log + ( bits >>> 1 );
    }

    private boolean[] strToBin(String string) {
        boolean[] binary = new boolean[string.getBytes().length * 8];
        int y = 0;
        for(Byte b : string.getBytes()){
            double byteCode = b.hashCode();
            double currentBit = 128;
            for(int x = 0; x < 8; x++){
                if(byteCode > currentBit){
                    binary[(y * 8) + x] = true;
                    byteCode = byteCode - currentBit;
                } else {
                    binary[(y * 8) + x] = false;
                }
                currentBit = currentBit / (double) 2;
            }
            y++;
        }
        return binary;
    }

    /*public String mergeStrings(String str1, String str2){
        String biggest = str2;
        String smallest = str1;

        if(str1.length() > str2.length()){
            biggest = str1;
            smallest = str2;
        }

        double letterSkip = ((double) (biggest.length())) / ((double) (smallest.length()));
        double currentLetterSkip = 0;
        int nextLetter = 0;
        String finalStr = "";
        for(int x = 0; x < biggest.length(); x++){
            if(currentLetterSkip > letterSkip){
                currentLetterSkip = currentLetterSkip - letterSkip;
                finalStr = finalStr + smallest.charAt(nextLetter);
                nextLetter++;
            }
            currentLetterSkip++;
            finalStr = finalStr + biggest.charAt(x);
        }
        return finalStr;

    }

    private void displayBinary(boolean[] binary){
        for(boolean b : binary) {
            if (b) {
                System.out.print("1");
            } else {
                System.out.print("0");
            }
        }
        System.out.println();
    }

    public int getVowels(String str){
        int vowelCount = 0;
        for (int i = str.length() - 1; i >= 0; i--) {//outer loop
            if(vowels.contains(str.charAt(i))){
                vowelCount++;
            }
        }
        return vowelCount;
    }*/

    public static void main(String[] args) throws UnsupportedEncodingException {
        Main main = new Main("Aje(Tm^cI7ulG&rylJ_AHKk)Iiq)KrhF");
        System.out.println(main.hashString("WhbtIsYourName"));
        System.out.println(main.hashString("WhatIsYourName"));
    }
}
