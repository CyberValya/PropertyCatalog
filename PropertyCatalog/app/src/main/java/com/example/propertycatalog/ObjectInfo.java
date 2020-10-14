package com.example.propertycatalog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ObjectInfo extends Activity {

    ImageView imageView;
    TextView priceText, squareText, roomsText, floorText, addresText, priceForSqM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.object);
        imageView = findViewById(R.id.imageView2);
        priceText = findViewById(R.id.price);
        squareText = findViewById(R.id.square);
        roomsText = findViewById(R.id.rooms);
        floorText = findViewById(R.id.floor);
        addresText = findViewById(R.id.addres);
        priceForSqM = findViewById(R.id.priceForSqM);

        Intent intent = getIntent();

        SaleObject saleObject = (SaleObject) intent.getExtras().get("object");
        Bitmap bitmap = (Bitmap) intent.getExtras().get("image");

        imageView.setImageBitmap(bitmap);
        priceText.setText(String.valueOf(saleObject.getPrice()));
        squareText.setText(String.valueOf(saleObject.getSquare()));
        roomsText.setText(String.valueOf(saleObject.getRooms()));
        floorText.setText(String.valueOf(saleObject.getFloor()));
        addresText.setText(saleObject.getAddress());
        priceForSqM.setText(String.valueOf(saleObject.getPriceForSqM()));
    }
}
