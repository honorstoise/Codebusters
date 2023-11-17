package com.example.zheng.codebusters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.textservice.SpellCheckerSession;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CipherActivity extends AppCompatActivity {

    private String cipher;
    private String mode = "Encode";
    private ArrayList<String> dictionary = new ArrayList<>();
    private ArrayList<String> hill = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cipher);

        Intent parent = getIntent();
        cipher = parent.getStringExtra("Cipher Name");
        TextView cipherView = findViewById(R.id.textView_Cipher);
        cipherView.setText(cipher + " Cipher");

        EditText inputText = findViewById(R.id.EditText_Input);
        EditText keyText = findViewById(R.id.EditText_Key);
        TextView outputView = findViewById(R.id.textView_Output);

        ImageButton submitButton = findViewById(R.id.imageButton_Submit);
        submitButton.setEnabled(false);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mode.equals("Encode")){
                    outputView.setText(encodeHelper(cipher, inputText.getText().toString().toLowerCase(), keyText.getText().toString().toLowerCase()));
                }else{
                    outputView.setText(decodeHelper(cipher, inputText.getText().toString().toLowerCase(), keyText.getText().toString().toLowerCase()));
                }
            }
        });

        Switch modeSwitch = findViewById(R.id.switch_Mode);
        modeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    mode = "Decode";
                    submitButton.setEnabled(true);
                }else{
                    mode = "Encode";
                    if(keyText.getText().length() > 0){
                        submitButton.setEnabled(true);
                    }else{
                        submitButton.setEnabled(false);
                    }
                }
            }
        });

        keyText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(mode == "Encode") {
                    if (keyText.getText().length() > 0) {
                        submitButton.setEnabled(true);
                    } else {
                        submitButton.setEnabled(false);
                    }
                }
            }
        });

        Button menuButton = findViewById(R.id.button_Menu);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), MenuActivity.class);
                Intent parent = getIntent();
                boolean showDialog = parent.getBooleanExtra("Show Dialog", true);
                startIntent.putExtra("Show Dialog", showDialog);
                startActivity(startIntent);
            }
        });
        fillDictionary();
        if(cipher.equals("Hill")){
            getHillKeys();
        }
    }

    //sets what cipher to encode once input is submitted
    public String encodeHelper(String cipher, String input, String key){
        if(cipher.equals("K1 Aristocrat")){
            return K1Aristocrat.encode(input, key);
        }else if(cipher.equals("Hill")){
            return Hill.encode(input, key);
        }else if(cipher.equals("Affine")){
            return Affine.encode(input, key);
        }else if(cipher.equals("Caesar")){
            return Caesar.encode(input, key);
        }else if(cipher.equals("Vigenere")){
            return Vigenere.encode(input, key);
        }
        return "cipher not picked";
    }

    //sets what cipher to decode and makes sure the solution has real words
    public String decodeHelper(String cipher, String input, String key){
        if(cipher.equals("K1 Aristocrat")) {
            if (key.length() > 0) {
                return K1Aristocrat.decode(input, key);
            } else {
                int realWords = 0;
                String returnMessage = "No solutions found";
                int returns = 1;
                for (String currentKey : dictionary) {
                    String currentMessage = K1Aristocrat.decode(input, currentKey);
                    System.out.println("Key: " + currentKey + ", Result: " + currentMessage);
                    String[] message = currentMessage.split(" ");
                    int realWordsInMessage = checkRealWordCount(currentMessage);
                    if(returns == 4){
                        break;
                    }else if (realWordsInMessage > realWords) {
                        realWords = realWordsInMessage;
                        returnMessage = currentMessage + "\nKey used: " + currentKey;
                    }else if (realWordsInMessage == message.length) {
                        returnMessage += "\n" + currentMessage + "\nKey used: " + currentKey;
                        returns += 1;
                    }
                }
                return returnMessage;
            }
        }else if(cipher.equals("Hill")){
            String inputWithoutSpaces = input.replace(" ", "");
            if(inputWithoutSpaces.length() % 2 == 0) {
                if (key.length() > 0) {
                    return Hill.decode(input, key);
                } else {
                    String returnMessage = "No solutions found";
                    int realWords = 0;
                    int returns = 1;
                    for(String currentKey: hill){
                        String currentMessage = Hill.decode(input, currentKey);
                        System.out.println("Key: " + currentKey + ", Result: " + currentMessage);
                        int realWordsInMessage = checkRealWordCount(currentMessage);
                        String[] inputMessage = input.split(" ");
                        if(returns == 4){
                            break;
                        }else if (realWordsInMessage > realWords) {
                            realWords = realWordsInMessage;
                            returnMessage = currentMessage + "\nKey used: " + currentKey;
                        }else if (realWordsInMessage == inputMessage.length) {
                            returnMessage += "\n" + currentMessage + "\nKey used: " + currentKey;
                            returns += 1;
                        }
                    }
                    return returnMessage;
                }
            }else{
                return "input needs an even number of letters";
            }
        }else if(cipher.equals("Affine")){
            if(key.length() > 0){
                return Affine.decode(input, key);
            }else{
                int realWords = 0;
                String returnMessage = "No solutions found";
                int returns = 1;
                int[] possibleKeys = {1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};
                for (int currentKey : possibleKeys) {
                    for(int i = 1; i <= 26; i++) {
                        String currentMessage = Affine.decode(input, currentKey + "," + i);
                        System.out.println("Key: " + currentKey + ", Result: " + currentMessage);
                        int realWordsInMessage = checkRealWordCount(currentMessage);
                        String[] message = currentMessage.split(" ");
                        if(returns == 4){
                            break;
                        }else if (realWordsInMessage > realWords) {
                            realWords = realWordsInMessage;
                            returnMessage = currentMessage + "\nKey used: a = " + currentKey + ", b = " + i;
                        }else if (realWordsInMessage == message.length) {
                            returnMessage += "\n" + currentMessage + "\nKey used: a = " + currentKey + ", b = " + i;
                            returns += 1;
                        }
                    }
                }
                return returnMessage;
            }
        }else if(cipher.equals("Caesar")){
            if(key.length() > 0){
                return Caesar.decode(input, key);
            }else{
                int realWords = 0;
                String returnMessage = "No solutions found";
                int returns = 1;
                for (int i = 0; i < 26; i++) {
                    String currentMessage = Caesar.decode(input, String.valueOf(i));
                    System.out.println("Key: " + i + ", Result: " + currentMessage);
                    String[] message = currentMessage.split(" ");
                    int realWordsInMessage = checkRealWordCount(currentMessage);
                    if(returns == 4){
                        break;
                    }else if (realWordsInMessage > realWords) {
                        realWords = realWordsInMessage;
                        returnMessage = currentMessage + "\nKey used: " + i;
                    }else if (realWordsInMessage == message.length) {
                        returnMessage += "\n" + currentMessage + "\nKey used: " + i;
                        returns += 1;
                    }
                }
                return returnMessage;
            }
        }else if(cipher.equals("Vigenere")){
            if(key.length() > 0){
                return Vigenere.decode(input, key);
            }else{
                int realWords = 0;
                String returnMessage = "No solutions found";
                int returns = 1;
                for (String currentKey : dictionary) {
                    String currentMessage = Vigenere.decode(input, currentKey);
                    System.out.println("Key: " + currentKey + ", Result: " + currentMessage);
                    String[] message = currentMessage.split(" ");
                    int realWordsInMessage = checkRealWordCount(currentMessage);
                    if(returns == 4){
                        break;
                    }else if (realWordsInMessage > realWords) {
                        realWords = realWordsInMessage;
                        returnMessage = currentMessage + "\nKey used: " + currentKey;
                    }else if (realWordsInMessage == message.length) {
                        returnMessage += "\n" + currentMessage + "\nKey used: " + currentKey;
                        returns += 1;
                    }
                }
                return returnMessage;
            }
        }
        return "cipher not picked";
    }

    public void fillDictionary(){
        String currentWord = "";
        InputStream is = this.getResources().openRawResource(R.raw.dictionary);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while (true) {
            try {
                if ((currentWord = reader.readLine()) == null) break;
            } catch (Exception e) {
                e.printStackTrace();
            }
            dictionary.add(currentWord);
        }
        try {
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getHillKeys(){
        String currentWord = "";
        InputStream is = this.getResources().openRawResource(R.raw.hill);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while (true) {
            try {
                if ((currentWord = reader.readLine()) == null) break;
            } catch (Exception e) {
                e.printStackTrace();
            }
            hill.add(currentWord);
        }
        try {
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //checks the amount of real words in a String
    public int checkRealWordCount(String input){
        int realWordsInMessage = 0;
        String[] message = input.split(" ");
        for (String word : message) {
            if (dictionary.contains(word)) {
                realWordsInMessage += 1;
            }
        }
        return realWordsInMessage;
    }
}