package com.example.movie2.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie2.Database.DatabaseHelper;
import com.example.movie2.Model.MovieItem;
import com.example.movie2.R;

import java.util.ArrayList;

public class SmallBackDropRecViewAdp extends RecyclerView.Adapter<SmallBackDropRecViewAdp.ViewHolder> {

    private static final String url = "https://image.tmdb.org/t/p/w1280";
    private Context context;
    private ArrayList<MovieItem> items = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;


    public SmallBackDropRecViewAdp(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getReadableDatabase();
    }

    public void setItems(ArrayList<MovieItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void clearItems() {
        this.items.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SmallBackDropRecViewAdp.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.small_backdrop_rec_view_item, parent, false);
        return new SmallBackDropRecViewAdp.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmallBackDropRecViewAdp.ViewHolder holder, final int position) {
        Glide.with(context)
                .asBitmap()
                .load(url + items.get(position).getBackdrop_path())
                .into(holder.imageView);

        final int pos = position;
        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final MovieItem item = items.get(pos);

                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Deleting" + item.getTitle())
                        .setMessage("Are you sure you want to delete " + item.getTitle())
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseHelper.delete(database, item);
                        items.remove(item);
                        notifyItemRemoved(pos);
                        Toast.makeText(context, "Deleted " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                }).create().show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            parent = itemView.findViewById(R.id.parent);
        }
    }

}
