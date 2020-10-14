package com.example.propertycatalog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ObjectAdapter extends RecyclerView.Adapter<Holder> {

    Context context;
    ArrayList<SaleObject> list;

    public ObjectAdapter(Context context, ArrayList<SaleObject> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        holder.price.setText(String.valueOf(list.get(position).getPrice()));
        String str = list.get(position).getAddress() + " " + String.valueOf(list.get(position).getSquare());
        holder.other.setText(str);
//        Glide.with(context).load(list.get(position).getImage()).into(holder.imageView);
        Picasso.with(context).load(list.get(position).getImage()).into(holder.imageView);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                SaleObject saleObject = list.get(position);
                BitmapDrawable bitmapDrawable = (BitmapDrawable) holder.imageView.getDrawable();
//                Bitmap bitmap = bitmapDrawable.getBitmap();
                Intent intent= new Intent(context, objectInformation.class);
                intent.putExtra("image", list.get(position).getImage().toString());
                intent.putExtra("price", String.valueOf(list.get(position).getPrice()));
                intent.putExtra("addres", list.get(position).getAddress());
                intent.putExtra("rooms", String.valueOf(list.get(position).getRooms()));
                intent.putExtra("floor", String.valueOf(list.get(position).getFloor()));
                intent.putExtra("square", String.valueOf(list.get(position).getSquare()));
                intent.putExtra("priceForSqM", String.valueOf(list.get(position).getPriceForSqM()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
