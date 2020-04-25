package com.example.movie2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie2.Model.Cast;

import java.util.ArrayList;

public class CastItemAdapter extends RecyclerView.Adapter<CastItemAdapter.ViewHolder> {
    private static final String TAG = "CastItemAdapter";
    private Context context;
    private ArrayList<Cast> items = new ArrayList<>();

    public CastItemAdapter(Context context) {
        this.context = context;
    }

    public CastItemAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_rec_view_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.txtItemName.setText(items.get(position).getName());
        holder.txtInMovieName.setText(items.get(position).getCharacter());

        String url = "https://image.tmdb.org/t/p/w600_and_h900_bestv2";

        Glide.with(context)
                .asBitmap()
                .load(url + items.get(position).getProfile_path())
                .into(holder.itemImage);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }


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


    public void setItems(ArrayList<Cast> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
