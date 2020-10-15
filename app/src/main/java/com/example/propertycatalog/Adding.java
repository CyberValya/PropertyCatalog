package com.example.propertycatalog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.service.autofill.DateTransformation;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Adding extends Activity
{
    Button setPicture;
    Button add;
    Uri image;
    DatabaseReference databaseRoot;
    StorageReference storageRoot;
    String objectImage = "Sale Objects";
    String currentUID = "id" + (new Date()).toString();
    EditText addSquare, addPrice, addAddres, addRooms, addFloor;
    final String FILENAME = "info";
    DatabaseHelper dbh;
    String username = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data != null){
            image = CropImage.getActivityResult(data).getUri();
            StorageReference path = storageRoot.child(objectImage).child(currentUID);
            path.putFile(image);
        };
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding);
        username =  getIntent().getExtras().get("user").toString();
        image = null;
        setPicture = findViewById(R.id.setPicture);
        final Activity activity= this;
        storageRoot = FirebaseStorage.getInstance().getReference();
        View.OnClickListener ocSetPicture = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setAspectRatio(2,1)
                        .setRequestedSize(800,400)
                        .start(activity);
            }
        };
        setPicture.setOnClickListener(ocSetPicture);
        dbh = new DatabaseHelper(this);

        add = findViewById(R.id.add);
        addSquare = findViewById(R.id.addSquare);
        addPrice = findViewById(R.id.addPrice);
        addAddres = findViewById(R.id.addAddres);
        addRooms = findViewById(R.id.addRooms);
        addFloor = findViewById(R.id.addFloor);
        View.OnClickListener ocAdd = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(addSquare.getText().toString().equals("") || addPrice.getText().toString().equals("") || addAddres.getText().toString().equals("") ||
                    addRooms.getText().toString().equals("") || addFloor.getText().toString().equals("") || image == null){
                    Snackbar.make(add, "Заполните все поля и загрузите фото!", Snackbar.LENGTH_LONG).show();
                }
                else{
                    Double price = Double.parseDouble(addPrice.getText().toString());
                    String addres = addAddres.getText().toString();
                    int rooms = Integer.parseInt(addRooms.getText().toString());
                    int floor = Integer.parseInt(addFloor.getText().toString());
                    Double square = Double.parseDouble(addSquare.getText().toString());
//                    SaleObject saleObject = new SaleObject(image, price, addres, rooms, floor, square);
                    if(dbh.insertObject(username, image.toString(), price, addres, rooms, floor, square)){
                        Snackbar.make(v, "Объект успешно добавлен", Snackbar.LENGTH_SHORT).show();
//                        intent.putExtra("user", username);
                        setResult(RESULT_OK);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                finish();
                            }
                        }, 1000);
                    }
                    else{
                        Snackbar.make(v, "Что-то пошло не так :(", Snackbar.LENGTH_SHORT).show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                finish();
                            }
                        }, 1000);
                    }
//                    try {
//                        //поток для записи параметров объекта
//                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(FILENAME, MODE_PRIVATE)));
//                        //файл выглядит так:
//                        //image
//                        //price
//                        //addres
//                        //rooms
//                        //floor
//                        //square
//                        bw.write(saleObject.getImage()+"\n" + saleObject.getPrice()+"\n"+
//                                saleObject.getAddress()+"\n"+saleObject.getRooms()+"\n"+
//                                saleObject.getFloor()+"\n"+saleObject.getSquare()
//                        );
//                        bw.close();
//                    } catch (FileNotFoundException e) {
//                        Snackbar.make(add, "Что-то пошло не так :(", Snackbar.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        Snackbar.make(add, "Что-то пошло не так :(", Snackbar.LENGTH_LONG).show();
//                    }

//                    FirebaseDatabase.getInstance().getReference().setValue(saleObject);
                    //FirebaseDatabase db = FirebaseDatabase.getInstance();
                    //DatabaseReference dbRef = db.getReference(saleObject.getAddress());
                    //String obj = saleObject.getImage()+">" + saleObject.getPrice()+">"+ saleObject.getAddress()+">"+saleObject.getRooms()+">"+ saleObject.getFloor()+">"+saleObject.getSquare();
                    //dbRef.setValue(obj);

//                    try {
//                        //поток для чтения параметров объекта
//                        BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(FILENAME)));
//                        //файл выглядит так:
//                        //image
//                        Uri imageF = Uri.parse(br.readLine());
//                        //price
//                        Double priceF = Double.parseDouble(br.readLine());
//                        //addres
//                        String addresF = br.readLine();
//                        //rooms
//                        int roomsF = Integer.parseInt(br.readLine());
//                        //floor
//                        int floorF = Integer.parseInt(br.readLine());
//                        //square
//                        Double squareF = Double.parseDouble(br.readLine());
//                        br.close();
//                        saleObject = new SaleObject(imageF, priceF, addresF, roomsF, floorF, squareF);
//                        Snackbar.make(add, "Объект успешно добавлен!", Snackbar.LENGTH_LONG).show();
//                        FirebaseDatabase.getInstance().getReference().setValue(saleObject);
//                        saleObject = new SaleObject();
                    //displayAllObjects();
//                    } catch (FileNotFoundException e) {
//                        Snackbar.make(add, "Что-то пошло не так :(", Snackbar.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        Snackbar.make(add, "Что-то пошло не так :(", Snackbar.LENGTH_LONG).show();
//                    }
                }
            }
        };
        add.setOnClickListener(ocAdd);
    }
}