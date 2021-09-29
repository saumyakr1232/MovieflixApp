package com.example.movie2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie2.MovieItemAdapter;
import com.example.movie2.MyAsyncTask;
import com.example.movie2.R;

import java.util.ArrayList;

public class MainRecViewAdapter extends RecyclerView.Adapter<MainRecViewAdapter.ViewHolder> {
    private static final String TAG = "MainRecViewAdapter";

    private static final int BACKDROP_IMAGE_ITEM = 1;

    private ArrayList<String> sections = new ArrayList<>();

    private Context context;

    public MainRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            default:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_rec_view_item, parent, false);
                return new ViewHolder(view2);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieItemAdapter adapter = new MovieItemAdapter(context);
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.addItemDecoration(new RecItemDecorator(0, 12, 0, 0));
        holder.categoryTextView.setText(sections.get(position));
        MyAsyncTask task1 = new MyAsyncTask(adapter, context, sections.get(position));
        task1.execute();


    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }


    public void setSections(ArrayList<String> sections) {
        this.sections = sections;
        notifyDataSetChanged();
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
