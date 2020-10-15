package com.example.propertycatalog;

import android.content.ClipData;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView imageView;
    TextView price, other;
    ItemClickListener itemClickListener;

    public Holder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.objImage);
        price = itemView.findViewById(R.id.objPrice);
        other = itemView.findViewById(R.id.objDes);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClickListener(v, getLayoutPosition());
    }
    public void setItemClickListener(ItemClickListener ic){
        this.itemClickListener = ic;
    }
}
