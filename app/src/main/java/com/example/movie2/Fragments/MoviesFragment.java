package com.example.movie2.Fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.example.movie2.Database.DatabaseAsyncTask;
import com.example.movie2.Database.DatabaseObserver;
import com.example.movie2.Database.LocalStorageDb;
import com.example.movie2.HomeActivity;
import com.example.movie2.R;

import java.util.ArrayList;

public class MoviesFragment extends Fragment implements DatabaseObserver {

    private RecyclerView mainRecView, bannerRecView, watchListRecView;
    private TextView seeAllWatchListTV;
    private MainRecViewAdapter mainRecViewAdapter;
    private BigBackDropRecViewAdp bigBackDropRecViewAdp;
    private SmallBackDropRecViewAdp smallBackDropRecViewAdp;
    private LocalStorageDb localStorageDb;
    private SQLiteDatabase database;
    private ArrayList<String> sections = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        initViews(view);

        localStorageDb = LocalStorageDb.getInstance(getContext());

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


        sections.add("Trending");
        sections.add("Now Playing");
        sections.add("Top Rated");
        mainRecViewAdapter.setSections(sections);

        DatabaseAsyncTask databaseAsyncTask = new DatabaseAsyncTask(getContext(), smallBackDropRecViewAdp, DatabaseAsyncTask.GET_WATCHLIST);
        databaseAsyncTask.execute();


        smallBackDropRecViewAdp.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                watchListRecView.smoothScrollToPosition(0);
            }
        });


        seeAllWatchListTV.setOnClickListener(v -> {
            HomeActivity activity = (HomeActivity) getActivity();
            activity.toWatchListFragment();
        });

        return view;
    }

    private void initViews(View view) {
        mainRecView = view.findViewById(R.id.mainRecView);
        bannerRecView = view.findViewById(R.id.bannerRecView);
        watchListRecView = view.findViewById(R.id.watchListRecView);
        seeAllWatchListTV = view.findViewById(R.id.seeAllTextView);
        bannerRecView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        mainRecView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        watchListRecView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));


    }

    @Override
    public void onItemAdded() {
        DatabaseAsyncTask databaseAsyncTask = new DatabaseAsyncTask(getContext(), smallBackDropRecViewAdp, DatabaseAsyncTask.GET_WATCHLIST);
        databaseAsyncTask.execute();
    }

    @Override
    public void onItemDeleted() {

    }

    @Override
    public void onDatabaseChanged() {

    }

    @Override
    public void onPause() {
        super.onPause();
        localStorageDb.removeDbObserver(this);
    }

    @Override
    public void onResume() {
        localStorageDb.registerDbObserver(this);
        super.onResume();
    }
}
