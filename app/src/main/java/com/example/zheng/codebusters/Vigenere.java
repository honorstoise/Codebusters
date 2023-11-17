package com.example.zheng.codebusters;

import java.util.ArrayList;

public class Vigenere {

    private static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    //encodes input given a key with Vigenere cipher
    public static String encode(String input, String key){
        ArrayList<Integer> spacePositions = new ArrayList<>();
        for (int i = input.indexOf(" "); i >= 0; i = input.indexOf(" ", i + 1)) {
            spacePositions.add(i);
        }
        input = input.replace(" ", "");

        String newKey = "";
        int currentPosition = 0;
        for(int i = 0; i < input.length(); i++){
            newKey += key.charAt(currentPosition % key.length());
            currentPosition += 1;
        }

        String returnMessage = "";
        for (int i = 0; i < input.length(); i++) {
            String letter = String.valueOf(input.charAt(i));
            int letterIndex = alphabet.indexOf(letter);
            String indexLetter = String.valueOf(newKey.charAt(i));
            int keyIndex = alphabet.indexOf(indexLetter);
            int newIndex = letterIndex + keyIndex;
            String newLetter = String.valueOf(alphabet.charAt(floorMod(newIndex, 26)));
            returnMessage += newLetter;
        }
        for (int i : spacePositions) {
            returnMessage = returnMessage.substring(0, i) + " " + returnMessage.substring(i);
        }
        return returnMessage;
    }

    //decodes input given a key with Vigenere cipher
    public static String decode(String input, String key){
        ArrayList<Integer> spacePositions = new ArrayList<>();
        for (int i = input.indexOf(" "); i >= 0; i = input.indexOf(" ", i + 1)) {
            spacePositions.add(i);
        }
        input = input.replace(" ", "");

        String newKey = "";
        int currentPosition = 0;
        for(int i = 0; i < input.length(); i++){
            newKey += key.charAt(currentPosition % key.length());
            currentPosition += 1;
        }

        String returnMessage = "";
        for (int i = 0; i < input.length(); i++) {
            String letter = String.valueOf(input.charAt(i));
            int letterIndex = alphabet.indexOf(letter);
            String indexLetter = String.valueOf(newKey.charAt(i));
            int keyIndex = alphabet.indexOf(indexLetter);
            int newIndex = letterIndex - keyIndex;
            String newLetter = String.valueOf(alphabet.charAt(floorMod(newIndex, 26)));
            returnMessage += newLetter;
        }
        for (int i : spacePositions) {
            returnMessage = returnMessage.substring(0, i) + " " + returnMessage.substring(i);
        }
        return returnMessage;
    }

    //finds a (mod b)
    public static int floorMod(int a, int b){
        int mod = a % b;
        if(mod < 0){
            mod += 26;
        }
        return mod;
    }
}
