package com.example.propertycatalog;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class Athorization extends Activity {

    EditText email, password;
    Button signIn;
    DatabaseHelper dbh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.signin);
        dbh = new DatabaseHelper(this);
        signIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String userEmail = email.getText().toString();
                String userPass = password.getText().toString();
                if(userEmail.equals("") || userPass.equals("")) {
                    Snackbar.make(v, "Заполните все поля", Snackbar.LENGTH_SHORT);
                }
                else{
                    if (dbh.checkingemail(userEmail)) {
                        if (dbh.insertUser(userEmail, userPass)) {
                            Snackbar.make(v, "Успешная регистрация :)", Snackbar.LENGTH_SHORT).show();
                            intent.putExtra("user", email.getText().toString());
                            setResult(RESULT_OK, intent);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    finish();
                                }
                            }, 1000);
                        }
                    }
                    else{
                        if (dbh.emailpass(userEmail, userPass)) {
                            Snackbar.make(v, "Добро пожаловать :)", Snackbar.LENGTH_SHORT).show();
                            intent.putExtra("user", email.getText().toString());
                            setResult(RESULT_OK, intent);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    finish();
                                }
                            }, 1000);
                        }
                        else{
                            Snackbar.make(v, "Пароль введен неверно :(", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}
