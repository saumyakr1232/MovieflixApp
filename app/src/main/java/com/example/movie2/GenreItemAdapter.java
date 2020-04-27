package com.example.movie2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie2.Model.Genre;

import java.util.ArrayList;

public class GenreItemAdapter extends RecyclerView.Adapter<GenreItemAdapter.ViewHolder> {
    private static final String TAG = "GenreItemAdapter";

    private ArrayList<Genre> items = new ArrayList<>();

    private Context context;

    public GenreItemAdapter() {
    }

    public GenreItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_recyclar_view_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.btnGenre.setText(items.get(position).getName());
        holder.btnGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GenreActivity.class);
                intent.putExtra("incomingGenre", items.get(position).getId());
                intent.putExtra("GenreName", items.get(position).getName());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button btnGenre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnGenre = (Button) itemView.findViewById(R.id.btnGenre);

        }
    }

    public void setItems(ArrayList<Genre> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
