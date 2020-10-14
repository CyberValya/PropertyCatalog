package com.example.propertycatalog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, "DataBase T", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user(email text primary key, password text)");
//        db.execSQL("Create table object(user text, image text primary key, price real, addres text, rooms integer, floor integer, square real)");
//        db.execSQL("Create table object(id integer primary key autoincrement, user text, image text, price text, addres text, rooms text, floor text, square text)");
        db.execSQL("Create table object(user text, image text primary key, price text, addres text, rooms text, floor text, square text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists object");
    }

    //добавление к базе пользователя
    public boolean insertUser(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long insert = db.insert("user", null, contentValues);
        if(insert == -1) return false; //неудача
        else return true; //успех
    }
    //проверка на существование email
    public boolean checkingemail(String Uemail){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email = ?",new String[]{Uemail});
        if (cursor.getCount() > 0) return false; //пользователь в базе
        else return true; //пользователь не зарегистрирован
    }
    //добавление объекта в базу
    public boolean insertObject(String user, String image, double price, String addres, int rooms, int floor, double square){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user", user);
        contentValues.put("image", image);
        contentValues.put("price", String.valueOf(price));
        contentValues.put("addres", addres);
        contentValues.put("rooms", String.valueOf(rooms));
        contentValues.put("floor", String.valueOf(floor));
        contentValues.put("square", String.valueOf(square));

//        contentValues.put("user", user);
//        contentValues.put("image", image);
//        contentValues.put("price", price);
//        contentValues.put("addres", addres);
//        contentValues.put("rooms", rooms);
//        contentValues.put("floor", floor);
//        contentValues.put("square", square);
        long insert = db.insert("object", null, contentValues);
        if(insert > 0) return true; //успех
        else return false; //неудача
    }
    //проверка пароля для юзера
    public boolean emailpass(String email, String pass){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email = ? and password = ?",new String[]{email, pass});
        if(cursor.getCount() > 0) return true; //пользователь верный
        else return false; //ошибка в полях
    }
    //проверка на соответствие владельца объекта
    public boolean checkingUser(String user, String image){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from object where user = ?  and image = ?", new String[]{user, image});
        if (cursor.getCount() > 0) return true; //пользователь владелец
        else return false; //пользователь не является владельцем объекта
    }
    //возврат списка объектов недвижимости
    public ArrayList<SaleObject> getObjects(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from object", null);
        ArrayList<SaleObject> list = new ArrayList<SaleObject>();
        if(cursor != null && cursor.moveToFirst()) {
            do {
                String user = cursor.getString(cursor.getColumnIndexOrThrow("user"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                String price = cursor.getString(cursor.getColumnIndexOrThrow("price"));
                String addres = cursor.getString(cursor.getColumnIndexOrThrow("addres"));
                String rooms = cursor.getString(cursor.getColumnIndexOrThrow("rooms"));
                String floor = cursor.getString(cursor.getColumnIndexOrThrow("floor"));
                String square = cursor.getString(cursor.getColumnIndexOrThrow("square"));
                list.add(new SaleObject(Uri.parse(image), Double.parseDouble(price), addres, Integer.parseInt(rooms), Integer.parseInt(floor),  Double.parseDouble(square)));
            } while (cursor.moveToNext());
        }
        return list;
    }
    //изменение объекта в базе
    public void changeObject(String _price, String _rooms, String _floor, String _square, String _image){
        SQLiteDatabase db = this.getWritableDatabase();
//        updateData(db, _image, "price", _price);
//        updateData(db, _image, "rooms", _rooms);
//        updateData(db, _image, "floor", _floor);
//        updateData(db, _image, "square", _square);
        updateData(db, _image, _price, _rooms, _floor, _square);
    }
//    private void updateData(SQLiteDatabase db, String _image, String column, String item, String _price, String _rooms, String _floor, String _square){
    private void updateData(SQLiteDatabase db, String _image, String _price, String _rooms, String _floor, String _square){
        Cursor cursor = db.rawQuery("Select * from object where image=?", new String[]{_image});
        ContentValues contentValues = new ContentValues();
        if(cursor != null && cursor.moveToFirst()) {
            String user = cursor.getString(cursor.getColumnIndexOrThrow("user"));
            String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
            String price = cursor.getString(cursor.getColumnIndexOrThrow("price"));
            String addres = cursor.getString(cursor.getColumnIndexOrThrow("addres"));
            String rooms = cursor.getString(cursor.getColumnIndexOrThrow("rooms"));
            String floor = cursor.getString(cursor.getColumnIndexOrThrow("floor"));
            String square = cursor.getString(cursor.getColumnIndexOrThrow("square"));

            contentValues.put("user", user);
            contentValues.put("image", image);
//            contentValues.put("price", price);
            contentValues.put("price", _price);
            contentValues.put("addres", addres);
//            contentValues.put("rooms", rooms);
//            contentValues.put("floor", floor);
//            contentValues.put("square", square);
            contentValues.put("rooms", _rooms);
            contentValues.put("floor", _floor);
            contentValues.put("square", _square);

            db.update("object", contentValues, "image=?", new String[]{_image});
        }
    }
}
