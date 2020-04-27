package com.example.movie2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie2.Model.MovieItems;

import java.util.ArrayList;

public class MovieItemAdapter extends RecyclerView.Adapter<MovieItemAdapter.ViewHolder> {
    private static final String TAG = "MovieItemAdapter";
    private Context context;
    private ArrayList<MovieItems> items = new ArrayList<>();
    private String type = "";
    private Utils utils;


    public MovieItemAdapter(Context context) {
        this.context = context;
        utils = new Utils(context);
    }

    public MovieItemAdapter() {

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_rec_view_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.name.setText(items.get(position).getTitle());
        String url = "https://image.tmdb.org/t/p/w1280";
        holder.date.setText(items.get(position).getRelease_date());
        holder.rating.setText(String.valueOf(items.get(position).getVote_average()));
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
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieItems item = items.get(position);
                ArrayList<MovieItems> wantToWatch = utils.getWantToWatchMovies();
                if (wantToWatch != null) {
                    Log.d(TAG, "onClick: wantToWatch " + wantToWatch.toString());


                    boolean flag = false;

                    for (MovieItems i : wantToWatch
                    ) {
                        if (i.getId() == item.getId()) {
                            flag = true;
                            break;
                        }

                    }
                    if (flag) {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                        builder.setMessage("You Already Added this Movie to your Watch List");

                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.setCancelable(false);
                        builder.create().show();


                    } else {
                        utils.addToWantToWatchMovies(item);
                        Toast.makeText(context, item.getTitle() + "is added to your Watch List", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final MovieItems item = items.get(position);

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
                            if (utils.removeWantToWatchBooks(items.get(position))) {
                                notifyDataSetChanged();
                                Toast.makeText(context, item.getTitle() + " has successfully deleted", Toast.LENGTH_SHORT).show();
                            }
                        }


                    }).create().show();
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image, btnAdd, imgStar;
        private TextView name, date, rating;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgStar = (ImageView) itemView.findViewById(R.id.imgStar);
            image = (ImageView) itemView.findViewById(R.id.itemImage);
            name = (TextView) itemView.findViewById(R.id.txtItemName);
            date = (TextView) itemView.findViewById(R.id.txtdate);
            rating = (TextView) itemView.findViewById(R.id.txtRating);
            btnAdd = (ImageView) itemView.findViewById(R.id.btnAdd);
            parent = (CardView) itemView.findViewById(R.id.parent);

        }
    }

    public void setItems(ArrayList<MovieItems> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setType(String type) {
        this.type = type;
    }
}
