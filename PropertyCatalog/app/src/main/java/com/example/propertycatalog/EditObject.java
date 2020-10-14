package com.example.propertycatalog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class EditObject extends AppCompatActivity {

    Button save;
    EditText price, square, floor, rooms;
    DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_object);
        save = findViewById(R.id.save);
        price = findViewById(R.id.enterPrice);
        square = findViewById(R.id.enterSquare);
        floor = findViewById(R.id.enterFloor);
        rooms = findViewById(R.id.enterRooms);
        Intent intent = getIntent();

        dbh = new DatabaseHelper(this);

        price.setText(intent.getStringExtra("price"));
        square.setText(intent.getStringExtra("square"));
        floor.setText(intent.getStringExtra("floor"));
        rooms.setText(intent.getStringExtra("rooms"));
        final String userName = intent.getStringExtra("userName");
        final String image = intent.getStringExtra("image");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(price.getText().toString().equals("") || square.getText().toString().equals("") ||
                        floor.getText().toString().equals("") || rooms.getText().toString().equals("")){
                    Snackbar.make(save, "Заполните все поля!", Snackbar.LENGTH_LONG).show();
                }
                else {
                    dbh.changeObject(price.getText().toString(), rooms.getText().toString(), floor.getText().toString(), square.getText().toString(), image);
                    Snackbar.make(save, "Успешно обновлено!", Snackbar.LENGTH_LONG).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Intent i = new Intent();
                            i.putExtra("price", price.getText().toString());
                            i.putExtra("square", square.getText().toString());
                            i.putExtra("floor", floor.getText().toString());
                            i.putExtra("rooms", rooms.getText().toString());
                            setResult(RESULT_OK, i);
//                            getParent().finish();
                            finish();
                        }
                    }, 1000);
                }
            }
        });
    }

}