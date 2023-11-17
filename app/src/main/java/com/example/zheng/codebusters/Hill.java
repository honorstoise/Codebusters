package com.example.zheng.codebusters;

import java.util.ArrayList;

public class Hill {

    private static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    //encodes input given a key with Hill Cipher
    public static String encode(String input, String key){
        if(key.length() == 4) {
            int determinant = (letterToNumber(key.charAt(0)) * (letterToNumber(key.charAt(3))) - letterToNumber(key.charAt(1)) * (letterToNumber(key.charAt(2))));
            int gcd = greatestCommonDivisor(floorMod(determinant, 26), 26);
            if (gcd == 1) {
                String inputWithoutSpaces = input.replace(" ", "");
                if (inputWithoutSpaces.length() % 2 == 1) {
                    input += "q";
                }
                ArrayList<Integer> spacePositions = new ArrayList<>();
                for (int i = input.indexOf(" "); i >= 0; i = input.indexOf(" ", i + 1)) {
                    spacePositions.add(i);
                }
                input = input.replace(" ", "");
                String returnMessage = "";
                for (int i = 0; i < input.length(); i += 2) {
                    int encoded1 = (letterToNumber(key.charAt(0)) * letterToNumber(input.charAt(i))
                            + letterToNumber(key.charAt(1)) * letterToNumber(input.charAt(i + 1))) % 26;
                    int encoded2 = (letterToNumber(key.charAt(2)) * letterToNumber(input.charAt(i))
                            + letterToNumber(key.charAt(3)) * letterToNumber(input.charAt(i + 1))) % 26;
                    returnMessage += numberToLetter(encoded1);
                    returnMessage += numberToLetter(encoded2);
                }
                for (int i : spacePositions) {
                    returnMessage = returnMessage.substring(0, i) + " " + returnMessage.substring(i);
                }
                return returnMessage;
            } else {
                return "key needs to have a valid determinant";
            }
        }else{
            return "key needs to be 4 letters long";
        }
    }

    //decodes input given a key with Hill cipher
    public static String decode(String input, String key){
        if(key.length() == 4) {
            int determinant = (letterToNumber(key.charAt(0)) * (letterToNumber(key.charAt(3))) - letterToNumber(key.charAt(1)) * (letterToNumber(key.charAt(2))));
            int gcd = greatestCommonDivisor(floorMod(determinant, 26), 26);
            int product = 0;
            int[] inverseMatrix = {0, 0, 0, 0};
            if (floorMod(determinant, 26) != 0 && gcd == 1) {
                int multiplicativeInverse = 0;
                while (floorMod(product, 26) != 1) {
                    multiplicativeInverse++;
                    product = (floorMod(determinant, 26)) * multiplicativeInverse;
                }
                inverseMatrix[0] = floorMod(letterToNumber(key.charAt(3)) * multiplicativeInverse, 26);
                inverseMatrix[1] = floorMod(-letterToNumber(key.charAt(1)), 26) * multiplicativeInverse % 26;
                inverseMatrix[2] = floorMod(-letterToNumber(key.charAt(2)), 26) * multiplicativeInverse % 26;
                inverseMatrix[3] = floorMod(letterToNumber(key.charAt(0)) * multiplicativeInverse, 26);

                ArrayList<Integer> spacePositions = new ArrayList<>();
                for (int i = input.indexOf(" "); i >= 0; i = input.indexOf(" ", i + 1)) {
                    spacePositions.add(i);
                }
                input = input.replace(" ", "");
                String returnMessage = "";
                for (int i = 0; i < input.length(); i += 2) {
                    int decoded1 = (floorMod((inverseMatrix[0] * letterToNumber(input.charAt(i)) + inverseMatrix[1] * letterToNumber(input.charAt(i + 1))), 26));
                    int decoded2 = (floorMod((inverseMatrix[2] * letterToNumber(input.charAt(i)) + inverseMatrix[3] * letterToNumber(input.charAt(i + 1))), 26));
                    returnMessage += numberToLetter(decoded1);
                    returnMessage += numberToLetter(decoded2);
                }
                for (int i : spacePositions) {
                    returnMessage = returnMessage.substring(0, i) + " " + returnMessage.substring(i);
                }
                return returnMessage;
            } else {
                return "";
            }
        }else{
            return "key needs to be 4 letters long";
        }
    }

    //returns position of letter in alphabet
    public static int letterToNumber(char letter){
        return alphabet.indexOf(String.valueOf(letter));
    }

    //returns letter given a number position in the alphabet
    public static String numberToLetter(int number){
        return String.valueOf(alphabet.charAt(number));
    }

    //finds the greatest common divisor of a and b
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
