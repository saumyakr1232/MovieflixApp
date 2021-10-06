package com.example.movie2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie2.Database.LocalStorageDb;
import com.example.movie2.Model.MovieItem;

import java.util.ArrayList;

public class MovieItemAdapter extends RecyclerView.Adapter<MovieItemAdapter.ViewHolder> {
    private static final String TAG = "MovieItemAdapter";
    private Context context;
    private ArrayList<MovieItem> items = new ArrayList<>();
    private String type = "";

    private LocalStorageDb localStorageDb;
    private SQLiteDatabase database;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
//        holder.name.setText(items.get(position).getTitle());
        String url = "https://image.tmdb.org/t/p/w1280";
//        holder.date.setText(items.get(position).getRelease_date());
//        holder.rating.setText(String.valueOf(items.get(position).getVote_average()));
        Glide.with(context)
                .asBitmap()
                .load(url + items.get(position).getPoster_path())
                .into(holder.image);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MovieItemActivity.class);
                intent.putExtra("item", items.get(position));
                context.startActivity(intent);

            }
        });



        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final MovieItem item = items.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Deleting" + item.getTitle())
                        .setMessage("Are you sure you want to delete " + item.getTitle())
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                if ("want to watch".equals(type)) {
                    builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                deleteMovie = (DeleteMovie) context;
                                deleteMovie.onDeletingResult(items.get(position));
                            } catch (ClassCastException e) {
                                e.printStackTrace();
                            }
                        }


                    }).create().show();
                }
                return true;
            }
        });

    }

    public void setItems(ArrayList<MovieItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    private DeleteMovie deleteMovie;

    private AddMovie addMovie;


    public MovieItemAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_rec_view_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        localStorageDb = new LocalStorageDb(context);
        database = localStorageDb.getReadableDatabase();
        return holder;
    }

    public interface DeleteMovie {
        void onDeletingResult(MovieItem movie);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clearItems() {
        this.items.clear();
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image, btnAdd;
        //                , imgStar;
//        private TextView name;
//        date, rating;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            imgStar = (ImageView) itemView.findViewById(R.id.imgStar);
            image = (ImageView) itemView.findViewById(R.id.itemImage);
//            name = (TextView) itemView.findViewById(R.id.txtItemName);
//            date = (TextView) itemView.findViewById(R.id.txtdate);
//            rating = (TextView) itemView.findViewById(R.id.txtRating);
            parent = (CardView) itemView.findViewById(R.id.parent);

        }
    }

    public interface AddMovie {
        void onAddingResult(MovieItem movie);
    }

    public void setType(String type) {
        this.type = type;
    }
}
