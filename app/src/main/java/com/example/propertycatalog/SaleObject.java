package com.example.propertycatalog;

import android.media.Image;
import android.net.Uri;

import java.io.Serializable;

public class SaleObject implements Serializable {
    private Uri image;
    private double price;
    private String address;
    private int rooms;
    private double priceForSqM;
    private int floor;
    private double square;

    public double getSquare() {
        return square;
    }

    public void setSquare(double square) {
        this.square = square;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public double getPriceForSqM() {
        return priceForSqM;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public SaleObject(Uri _image, double _price, String _addres, int _rooms, int _floor, double _square){
        this.image=_image;
        this.price=_price;
        this.address=_addres;
        this.rooms=_rooms;
        this.floor=_floor;
        this.square=_square;
        this.priceForSqM=getPrice()/getSquare();
    }
    public SaleObject(){}
}
