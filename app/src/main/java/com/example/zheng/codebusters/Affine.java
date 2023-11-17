package com.example.zheng.codebusters;

import java.util.ArrayList;

public class Affine {

    private static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private static int[] validAValues = {1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};

    //encodes input given a key with Affine Cipher
    public static String encode(String input, String key){
            String[] inputArray = input.split(" ");
            String returnMessage = "";
            String newKey = key.replace("a", "").replace("b", "").replace(" ", "").replace("=", "");
            String[] variables = newKey.split(",");
            int a = Integer.parseInt(String.valueOf(variables[0]));
            int b = floorMod(Integer.parseInt(String.valueOf(variables[1])), 26);
            boolean validA = false;
            for(int test: validAValues){
                if (test == a){
                    validA = true;
                }
            }
            if(validA) {
                for (String word : inputArray) {
                    for (int i = 0; i < word.length(); i++) {
                        String letter = String.valueOf(word.charAt(i));
                        int letterIndex = alphabet.indexOf(letter);
                        int newIndex = (a * letterIndex + b) % 26;
                        returnMessage += alphabet.charAt(newIndex);
                    }
                    returnMessage += " ";
                }
                return returnMessage;
            }else{
                return "a value needs to be coprime with 26";
            }
    }

    //decodes input given a key with Affine cipher
    public static String decode(String input, String key){
        String[] inputArray = input.split(" ");
        String returnMessage = "";
        String newKey = key.replace("a", "").replace("b", "").replace(" ", "").replace("=", "");
        String[] variables = newKey.split(",");
        int a = Integer.parseInt(String.valueOf(variables[0]));
        int b = Integer.parseInt(String.valueOf(variables[1]));
        for(String word: inputArray){
            int multiplicativeInverse = 0;
            int product = 0;
            while(product % 26 != 1){
                multiplicativeInverse++;
                product = (a * multiplicativeInverse);
            }
            for(int i = 0; i < word.length(); i++){
                String letter = String.valueOf(word.charAt(i));
                int letterIndex = alphabet.indexOf(letter);
                int newIndex = floorMod(multiplicativeInverse * (letterIndex - b), 26);
                returnMessage += alphabet.charAt(newIndex);
            }
            returnMessage += " ";
        }
        return returnMessage;
    }

    //finds greatest common divisor of a and b
    public static int greatestCommonDivisor(int a, int b){
        if(b == 0){
            return a;
        }else{
            return greatestCommonDivisor(b, a % b);
        }
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
