package com.example.movie2.Adapter;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecItemDecorator extends RecyclerView.ItemDecoration {
    private int left;
    private int right;
    private int top;
    private int bottom;


    public RecItemDecorator(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        outRect.right = right;
        outRect.left = left;
        outRect.top = top;
        outRect.bottom = bottom;

    }
}
