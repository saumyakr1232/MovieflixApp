package com.example.movie2.Fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.movie2.Adapter.BigBackDropRecViewAdp;
import com.example.movie2.Adapter.MainRecViewAdapter;
import com.example.movie2.Adapter.RecItemDecorator;
import com.example.movie2.Adapter.SmallBackDropRecViewAdp;
import com.example.movie2.Database.DatabaseHelper;
import com.example.movie2.R;

public class MoviesFragment extends Fragment {

    private RecyclerView mainRecView, bannerRecView, watchListRecView;
    private MainRecViewAdapter mainRecViewAdapter;
    private BigBackDropRecViewAdp bigBackDropRecViewAdp;
    private SmallBackDropRecViewAdp smallBackDropRecViewAdp;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        initViews(view);

        mainRecViewAdapter = new MainRecViewAdapter(getContext());
        bigBackDropRecViewAdp = new BigBackDropRecViewAdp();
        smallBackDropRecViewAdp = new SmallBackDropRecViewAdp(getContext());


        bannerRecView.addItemDecoration(new RecItemDecorator(32, 32, 0, 0));
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(bannerRecView);

        watchListRecView.addItemDecoration(new RecItemDecorator(0, 12, 0, 0));

        bannerRecView.setAdapter(bigBackDropRecViewAdp);
        mainRecView.setAdapter(mainRecViewAdapter);
        watchListRecView.setAdapter(smallBackDropRecViewAdp);


        return view;
    }

    private void initViews(View view) {
        mainRecView = view.findViewById(R.id.mainRecView);
        bannerRecView = view.findViewById(R.id.bannerRecView);
        watchListRecView = view.findViewById(R.id.watchListRecView);
        bannerRecView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        mainRecView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        watchListRecView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));


    }

}
