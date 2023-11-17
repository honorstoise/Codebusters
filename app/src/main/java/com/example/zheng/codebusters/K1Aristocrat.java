package com.example.zheng.codebusters;

import android.content.Context;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class K1Aristocrat {

    private static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    //encodes input given a key with K1 Aristocrat Cipher
    public static String encode(String input, String key){
        String newAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String[] inputArray = input.split(" ");
        String returnMessage = "";
        String newKey = "";
        for (int i = 0; i < key.length(); i++) {
            if (!newKey.contains(String.valueOf(key.charAt(i)))) {
                newKey += key.charAt(i);
            }
        }

        for (int i = 0; i < newKey.length(); i++) {
            newAlphabet = newAlphabet.replace(String.valueOf(newKey.charAt(i)), "");
        }

        String cipherAlphabet = newKey + newAlphabet;

        for(String word: inputArray) {
            for (int i = 0; i < word.length(); i++) {
                String letter = String.valueOf(word.charAt(i));
                int letterIndex = alphabet.indexOf(letter);
                returnMessage += cipherAlphabet.charAt(letterIndex);
            }
            returnMessage += " ";
        }
        return returnMessage;
    }

    //decodes input given a key with K1 Aristocrat cipher
    public static String decode(String input, String key){
        String newAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String[] inputArray = input.split(" ");
        String returnMessage = "";
        String newKey = "";
        for (int i = 0; i < key.length(); i++) {
            if (!newKey.contains(String.valueOf(key.charAt(i)))) {
                newKey += key.charAt(i);
            }
        }

        for (int i = 0; i < newKey.length(); i++) {
            newAlphabet = newAlphabet.replace(String.valueOf(newKey.charAt(i)), "");
        }

        String cipherAlphabet = newKey + newAlphabet;

        for(String word: inputArray) {
            String returnWord = "";
            for (int i = 0; i < word.length(); i++) {
                String letter = String.valueOf(word.charAt(i));
                int letterIndex = cipherAlphabet.indexOf(letter);
                returnWord += alphabet.charAt(letterIndex);
            }
            returnMessage += returnWord + " ";
        }
        return returnMessage;
    }
}
