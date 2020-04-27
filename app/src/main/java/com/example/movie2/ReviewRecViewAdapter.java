package com.example.movie2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie2.Model.SingleReview;

import java.util.ArrayList;

public class ReviewRecViewAdapter extends RecyclerView.Adapter<ReviewRecViewAdapter.ViewHolder> {
    private static final String TAG = "ReviewRecViewAdapter";
    private ArrayList<SingleReview> items = new ArrayList<>();

    private Context context;

    public ReviewRecViewAdapter(Context context) {
        this.context = context;
    }

    public ReviewRecViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_rec_view_list_items, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder reviews: called");
        holder.reviewContent.setText(items.get(position).getContent());
        holder.txtReviewAuthor.setText(items.get(position).getAuthor());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "review clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ReviewWebActivity.class);
                intent.putExtra("url", items.get(position).getUrl());
                context.startActivity(intent);
            }
        });
        holder.reviewContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "review clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ReviewWebActivity.class);
                intent.putExtra("url", items.get(position).getUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView reviewContent, txtReviewAuthor;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewContent = (TextView) itemView.findViewById(R.id.reviewContent);
            txtReviewAuthor = (TextView) itemView.findViewById(R.id.txtReviewAuthor);
            parent = (CardView) itemView.findViewById(R.id.parentCard);
        }
    }

    public void setItems(ArrayList<SingleReview> items) {
        Log.d(TAG, "setItems: called");
        this.items = items;
        notifyDataSetChanged();
    }
}
