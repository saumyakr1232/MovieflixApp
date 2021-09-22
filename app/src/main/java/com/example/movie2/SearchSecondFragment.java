package com.example.movie2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie2.Model.MovieItem;

import java.util.ArrayList;

public class SearchSecondFragment extends Fragment {
    private static final String TAG = "SearchSecondFragment";

    private RecyclerView searchResultRecView;
    private MovieItemAdapter searchMoviesItemAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_serach_fragment, container, false);

        searchResultRecView = (RecyclerView) view.findViewById(R.id.searchResultRecView);

        searchMoviesItemAdapter = new MovieItemAdapter(getActivity());

        searchResultRecView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        searchResultRecView.setAdapter(searchMoviesItemAdapter);


        return view;
    }

    public void setSearchResults(ArrayList<MovieItem> items) {

        searchMoviesItemAdapter = new MovieItemAdapter(getActivity());

        searchResultRecView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        searchResultRecView.setAdapter(searchMoviesItemAdapter);
        searchMoviesItemAdapter.setItems(items);
    }
}
