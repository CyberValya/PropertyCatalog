package com.example.propertycatalog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

public class objectInformation extends AppCompatActivity {

    ImageView imageView;
    TextView priceText, squareText, roomsText, floorText, addresText, priceForSqM;
    String _price, _square, _rooms, _floor, _image, _userName;
    Button back, edit;

    private void startEdit(){
        Intent intent = new Intent(this, EditObject.class);

        intent.putExtra("price", _price);
        intent.putExtra("rooms", _rooms);
        intent.putExtra("floor", _floor);
        intent.putExtra("square", _square);
        intent.putExtra("image", _image);
        intent.putExtra("userName", _userName);

        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_information);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edit = findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEdit();
            }
        });

        imageView = findViewById(R.id.imageView2);
        priceText = findViewById(R.id.price);
        squareText = findViewById(R.id.square);
        roomsText = findViewById(R.id.rooms);
        floorText = findViewById(R.id.floor);
        addresText = findViewById(R.id.addres);
        priceForSqM = findViewById(R.id.priceForSqM);

        Intent intent = getIntent();
        _price = intent.getStringExtra("price");
        _floor = intent.getStringExtra("rooms");
        _rooms = intent.getStringExtra("rooms");
        _square = intent.getStringExtra("square");
        _image  = intent.getStringExtra("image");
        _userName  = intent.getStringExtra("_userName");

        Picasso.with(this).load(intent.getStringExtra("image")).into(imageView);
        priceText.setText(intent.getStringExtra("price"));
        squareText.setText(intent.getStringExtra("square"));
        roomsText.setText(intent.getStringExtra("rooms"));
        floorText.setText(intent.getStringExtra("floor"));
        addresText.setText(intent.getStringExtra("addres"));
        priceForSqM.setText(intent.getStringExtra("priceForSqM"));

//        SaleObject saleObject = (SaleObject) intent.getExtras().get("object");
//        Bitmap bitmap = (Bitmap) intent.getExtras().get("image");

//        imageView.setImageBitmap(bitmap);
//        priceText.setText(String.valueOf(saleObject.getPrice()));
//        squareText.setText(String.valueOf(saleObject.getSquare()));
//        roomsText.setText(String.valueOf(saleObject.getRooms()));
//        floorText.setText(String.valueOf(saleObject.getFloor()));
//        addresText.setText(saleObject.getAddress());
//        priceForSqM.setText(String.valueOf(saleObject.getPriceForSqM()));
    }
}