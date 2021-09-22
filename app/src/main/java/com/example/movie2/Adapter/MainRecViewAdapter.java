package com.example.movie2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.movie2.MovieItemAdapter;
import com.example.movie2.MyAsyncTask;
import com.example.movie2.R;

public class MainRecViewAdapter extends RecyclerView.Adapter<MainRecViewAdapter.ViewHolder> {
    private static final String TAG = "MainRecViewAdapter";

    private static final int BACKDROP_IMAGE_ITEM = 1;

    private Context context;

    public MainRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case BACKDROP_IMAGE_ITEM:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.backdrop_image_movie_item, parent, false);
                ViewHolder holder = new ViewHolder(view);
                SnapHelper snapHelper = new PagerSnapHelper();
                snapHelper.attachToRecyclerView(holder.recyclerView);
                return holder;
            default:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_image_item, parent, false);
                return new ViewHolder(view2);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 0) {
            holder.recyclerView.setAdapter(new MovieRecViewAdapter());
            holder.recyclerView.addItemDecoration(new RecItemDecorator(32));

        } else {
            MovieItemAdapter adapter = new MovieItemAdapter(context);
            holder.recyclerView.setAdapter(adapter);
            holder.categoryTextView.setText("Some text " + position);
            MyAsyncTask task1 = new MyAsyncTask(adapter, context, 1);
            task1.execute();
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return BACKDROP_IMAGE_ITEM;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView categoryTextView, seeAllTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recyclerView = itemView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            seeAllTextView = itemView.findViewById(R.id.seeAllTextView);

        }
    }

}
