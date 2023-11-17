package com.example.zheng.codebusters;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    boolean showDialog = true;
    String cipherChoice = "K1 Aristocrat";
    AlertDialog.Builder dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button cipherButton = findViewById(R.id.button_Ciphers);
        cipherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCipherDialog();
            }
        });

        Intent parent = getIntent();
        showDialog = parent.getBooleanExtra("Show Dialog", true);
        if(showDialog){
            showDialog();
        }
    }

    //shows the startup dialog
    private void showDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Hello! Select \"Ciphers\" to use our cryptography tools.");
        dialogBuilder.setMultiChoiceItems(new String[]{"Don't Show Again"}, new boolean[]{false}, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                showDialog = !b;
            }
        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    //shows dialog for selecting the cipher to use
    private void selectCipherDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final String[] CIPHER_CHOICES = {"K1 Aristocrat", "Hill", "Affine", "Caesar", "Vigenere"};
        dialogBuilder.setTitle("Choose Cipher");
        dialogBuilder.setSingleChoiceItems(CIPHER_CHOICES, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cipherChoice = CIPHER_CHOICES[which];
            }
        });

        dialogBuilder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent startIntent = new Intent(getApplicationContext(), CipherActivity.class);
                startIntent.putExtra("Cipher Name", cipherChoice);
                startIntent.putExtra("Show Dialog", showDialog);
                startActivity(startIntent);
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }


}