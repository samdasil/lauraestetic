package com.example.lauraestetic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class InicioDialog extends AppCompatActivity {

    private static final String FILE_USER_DATA = "DataUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences(FILE_USER_DATA, 0); // mode = 0 (private) para que somente o app possa mudar o arquivo
        SharedPreferences.Editor editor = preferences.edit();

        //SharedPreferences dados = getSharedPreferences(FILE_USER_DATA, 0); // mode = 0 (private) para que somente o app possa mudar o arquivo

        if ( preferences.contains("nome") ) {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            intent.putExtra("nome", preferences.getString("nome", "Usuário não definido"));
            startActivity(intent);
            finish();
        } else {
            dialog();
        }

    }

    @SuppressLint("ResourceAsColor")
    public void dialog() {

        SharedPreferences preferences = getSharedPreferences(FILE_USER_DATA, 0);
        final SharedPreferences.Editor editor = preferences.edit();

        SharedPreferences dados = getSharedPreferences(FILE_USER_DATA, 0);

        AlertDialog.Builder inicio = new AlertDialog.Builder(InicioDialog.this);
        inicio.setTitle("Laura Estetic");

        final EditText input = new EditText(this);
        input.setGravity(3);
        input.setTextColor(R.color.colorTextDialog);
        inicio.setView(input);
        inicio.setIcon(R.drawable.ic_person_green_24dp);
        inicio.setNeutralButton("Entrar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                if ( input.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Preencha o nome", Toast.LENGTH_SHORT).show();
                } else {
                    String nome = input.getText().toString();
                    editor.putString("nome", nome);
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                    finish();

                }

            }

        });

        inicio.show();
        // FORÇA O TECLADO APARECER AO ABRIR O ALERT
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }
}
