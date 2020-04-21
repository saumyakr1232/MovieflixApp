package com.example.movie2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CastItemAdapter {
    private static final String TAG = "CastItemAdapter";


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImage;
        private TextView txtItemName, txtInMovieName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.itemImage);
            txtItemName = (TextView) itemView.findViewById(R.id.txtItemName);
            txtInMovieName = (TextView) itemView.findViewById(R.id.txtInMovieName);

        }
    }
}
