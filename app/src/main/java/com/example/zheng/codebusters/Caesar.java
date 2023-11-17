package com.example.zheng.codebusters;

public class Caesar {

    private static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    //encodes input given a key with Caesar Cipher
    public static String encode(String input, String key){
        String[] inputArray = input.split(" ");
        String returnMessage = "";
        for(String word: inputArray){
            for (int i = 0; i < word.length(); i++) {
                String letter = String.valueOf(word.charAt(i));
                int letterIndex = alphabet.indexOf(letter);
                int newIndex = floorMod((letterIndex + Integer.parseInt(key)), 26);
                returnMessage += alphabet.charAt(newIndex);
            }
            returnMessage += " ";
        }
        return returnMessage;
    }

    //decodes input given a key with Caesar cipher
    public static String decode(String input, String key){
            String[] inputArray = input.split(" ");
            String returnMessage = "";
            for(String word: inputArray){
                for (int i = 0; i < word.length(); i++) {
                    String letter = String.valueOf(word.charAt(i));
                    int letterIndex = alphabet.indexOf(letter);
                    int newIndex = floorMod((letterIndex - Integer.parseInt(key)), 26);
                    returnMessage += alphabet.charAt(newIndex);
                }
                returnMessage += " ";
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
