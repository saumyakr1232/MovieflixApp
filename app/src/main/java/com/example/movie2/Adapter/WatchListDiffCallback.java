package com.example.movie2.Adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.movie2.Model.MovieItem;

import java.util.List;

public class WatchListDiffCallback extends DiffUtil.Callback {
    private final List<MovieItem> mOldMovieItemList;
    private final List<MovieItem> mNewMovieItemList;

    public WatchListDiffCallback(List<MovieItem> mOldMovieItemList, List<MovieItem> mNewMovieItemList) {
        this.mNewMovieItemList = mNewMovieItemList;
        this.mOldMovieItemList = mOldMovieItemList;
    }

    @Override
    public int getOldListSize() {
        return mOldMovieItemList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewMovieItemList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldMovieItemList.get(oldItemPosition).getId() == mNewMovieItemList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final MovieItem oldMovieItem = mOldMovieItemList.get(oldItemPosition);
        final MovieItem newMovieItem = mNewMovieItemList.get(newItemPosition);
        return oldMovieItem.getBackdrop_path().equals(newMovieItem.getBackdrop_path())
                && oldMovieItem.getTitle().equals(newMovieItem.getTitle());
    }


    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
