package com.example.movie2.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie2.Database.LocalStorageDb;
import com.example.movie2.Model.MovieItem;
import com.example.movie2.R;

import java.util.ArrayList;
import java.util.List;

public class SmallBackDropRecViewAdp extends RecyclerView.Adapter<SmallBackDropRecViewAdp.ViewHolder> {

    private static final String URL = "https://image.tmdb.org/t/p/w1280";
    private Context context;
    private ArrayList<MovieItem> items = new ArrayList<>();
    private LocalStorageDb localStorageDb;
    private SQLiteDatabase database;
    private ArrayList<MovieItem> selectedItems = new ArrayList<>();
    private boolean isSelecting = false;


    public SmallBackDropRecViewAdp(Context context) {
        this.context = context;
        localStorageDb = new LocalStorageDb(context);
        database = localStorageDb.getReadableDatabase();
    }

    public void updateItems(List<MovieItem> movieItems) {
        final WatchListDiffCallback diffCallback = new WatchListDiffCallback(this.items, movieItems);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.items.clear();
        this.items.addAll(movieItems);

        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public SmallBackDropRecViewAdp.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.small_backdrop_rec_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmallBackDropRecViewAdp.ViewHolder holder, final int position) {
        Glide.with(context)
                .asBitmap()
                .load(URL + items.get(position).getBackdrop_path())
                .into(holder.imageView);


        if (isSelecting) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(false);
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    selectedItems.add(items.get(position));
                } else {
                    selectedItems.remove(items.get(position));
                }
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
                                dialog.dismiss();
                            }
                        });
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        localStorageDb.delete(database, item);
                        items.remove(item);
                        notifyItemRemoved(position);
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


    public void setSelecting(boolean selecting) {
        isSelecting = selecting;
        notifyItemRangeChanged(0, items.size());

    }


    public void removeSelected() {
        isSelecting = false;

        notifyItemRangeChanged(0, items.size());
        List<MovieItem> newItems = new ArrayList<>(items);
        newItems.removeAll(selectedItems);
        updateItems(newItems);

        selectedItems.forEach(item -> localStorageDb.delete(database, item));

    }

    public ArrayList<MovieItem> getSelectedItems() {
        return selectedItems;
    }

    public void addItems(ArrayList<MovieItem> newItems) {
        items.addAll(newItems);
        notifyItemRangeInserted(items.size() - newItems.size(), newItems.size());
    }


    protected static class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected CardView parent;
        protected CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            parent = itemView.findViewById(R.id.parent);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }

}
