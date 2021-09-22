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
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie2.Adapter.MainRecViewAdapter;
import com.example.movie2.DatabaseHelper;
import com.example.movie2.R;

public class MoviesFragment extends Fragment {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    private RecyclerView mainRecView;
    private MainRecViewAdapter movieRecViewAdapter;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        initViews(view);

        movieRecViewAdapter = new MainRecViewAdapter(getContext());

//
//        mainRecView.addItemDecoration(new RecItemDecorator(32));
//        SnapHelper snapHelper = new PagerSnapHelper();
//        snapHelper.attachToRecyclerView(mainRecView);


        mainRecView.setAdapter(movieRecViewAdapter);


        return view;
    }

    private void initViews(View view) {
        mainRecView = view.findViewById(R.id.mainRecView);
        mainRecView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

    }

}
