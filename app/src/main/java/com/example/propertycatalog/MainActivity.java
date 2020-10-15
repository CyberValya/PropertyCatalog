package com.example.propertycatalog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.ArrayList;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
//    private FirebaseListAdapter<SaleObject> adapter;
//    private static int SIGN_IN_CODE = 1;
    private RelativeLayout activity_main;
    private Button add, exit;
    final private int code = 0;
    private SaleObject saleObject;
    final String FILENAME = "info";
    public static String username = "";
    RecyclerView recyclerView;
    ObjectAdapter adapter;
    public static final int REQUEST_CODE = 100;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null && requestCode == 1){
            username = data.getStringExtra("user");
            Global.user = username;
            displayAllObjects();
        }
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
//            Toast.makeText(this, "Hello",Toast.LENGTH_LONG).show();
            displayAllObjects();
        }
        displayAllObjects();
//        if(requestCode == SIGN_IN_CODE)
//        {
//            if(resultCode == RESULT_OK) {
//                Snackbar.make(activity_main, "Вы авторизованы!", Snackbar.LENGTH_SHORT).show();
//                displayAllObjects();
//                try {
//                    //поток для чтения параметров объекта
//                    BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(FILENAME)));
//                    //файл выглядит так:
//                    //image
//                    Uri imageF = Uri.parse(br.readLine());
//                    //price
//                    Double priceF = Double.parseDouble(br.readLine());
//                    //addres
//                    String addresF = br.readLine();
//                    //rooms
//                    int roomsF = Integer.parseInt(br.readLine());
//                    //floor
//                    int floorF = Integer.parseInt(br.readLine());
//                    //square
//                    Double squareF = Double.parseDouble(br.readLine());
//                    br.close();
//                    saleObject = new SaleObject(imageF, priceF, addresF, roomsF, floorF, squareF);
//                    Snackbar.make(activity_main, "Объект успешно добавлен!", Snackbar.LENGTH_LONG).show();
//                    FirebaseDatabase.getInstance().getReference().setValue(saleObject);
//                    saleObject = new SaleObject();
//                    displayAllObjects();
//                } catch (FileNotFoundException e) {
//                    Snackbar.make(activity_main, "Что-то пошло не так :(", Snackbar.LENGTH_LONG).show();
//                } catch (IOException e) {
//                    Snackbar.make(activity_main, "Что-то пошло не так :(", Snackbar.LENGTH_LONG).show();
//                }
//            }
//            else
//            {
//                Snackbar.make(activity_main, "Вы не авторизованы!", Snackbar.LENGTH_SHORT).show();
//                finish();
//            }
//        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity_main = findViewById(R.id.activity_main);
        add = findViewById(R.id.add);
        exit = findViewById(R.id.exit);
        saleObject = new SaleObject();

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayAllObjects();

//        if(getIntent() != null) {
//            Intent i = getIntent();
//            if (i.getStringExtra("userName").length() > 0) {
//                username = i.getStringExtra("userName");
//            }
//        }

        if(username.equals("")) {
            newactivity();
            displayAllObjects();
        }

//        if(FirebaseAuth.getInstance().getCurrentUser() == null)
//        {
//            регистрируем пользователя, если он тут впервые
//            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_CODE);
//        }
//        else
//        {
//            Snackbar.make(activity_main, "Вы авторизованы!", Snackbar.LENGTH_SHORT).show();
//            displayAllObjects();
//        }

//        toolbar = findViewById(R.id.toolBarForObject);

        View.OnClickListener ocAdd = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newadd();
                displayAllObjects();
            }
        };
        View.OnClickListener ocExit = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = "";
                newactivity();
                displayAllObjects();
            }
        };
        add.setOnClickListener(ocAdd);
        exit.setOnClickListener(ocExit);


    }
    private void newactivity(){
        Intent intent = new Intent(this, Athorization.class);
        startActivityForResult(intent, 1);
    }
    private void newadd(){
        Intent intent = new Intent(this, Adding.class);
        intent.putExtra("user", username);
//        startActivity(intent);
        startActivityForResult(intent, REQUEST_CODE);
        displayAllObjects();
    }

    //загрузка объектов из базы
    public void displayAllObjects() {
        DatabaseHelper dbh = new DatabaseHelper(this);
        ArrayList<SaleObject> list = dbh.getObjects();

        adapter = new ObjectAdapter(this, list);
        recyclerView.setAdapter(adapter);
//        ListView listOfObjects = findViewById(R.id.list_of_objects);
//        DatabaseHelper dbh = new DatabaseHelper(this);
//        Cursor cursor = dbh.getObjects();
//        SimpleCursorAdapter adapter = null;
//        if(cursor.getCount() > 0){
//            adapter = new SimpleCursorAdapter(this, R.layout.objects_layout, cursor,
//                    new String[]{"price", "addres", "square"}, new int[]{R.id.price, R.id.addres, R.id.square}, 0);
//        }
//        listOfObjects.setAdapter(adapter);
//        cursor.close();
//        ListView listOfObjects = findViewById((R.id.list_of_objects));
//        adapter = new FirebaseListAdapter<SaleObject>(this, SaleObject.class, R.layout.objects_layout, FirebaseDatabase.getInstance().getReference()) {
//            @Override
//            protected void populateView(View v, SaleObject model, int position) {
//                TextView price, square, addres;
//                price = v.findViewById(R.id.price);
//                square = v.findViewById(R.id.square);
//                addres = v.findViewById(R.id.addres);
//
//                price.setText(Double.toString(model.getPrice()));
//                square.setText(Double.toString(model.getSquare()));
//                addres.setText(model.getAddress());
//            }
//        };
//        listOfObjects.setAdapter(adapter);
    }
}