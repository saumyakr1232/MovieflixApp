package com.example.movie2.Fragments;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie2.Adapter.RecItemDecorator;
import com.example.movie2.Adapter.SmallBackDropRecViewAdp;
import com.example.movie2.Database.DatabaseHelper;
import com.example.movie2.Model.MovieItem;
import com.example.movie2.R;
import com.example.movie2.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MyListFragment extends Fragment {
    private static final String TAG = "MyListFragment";

    private RecyclerView recyclerView;
    private SmallBackDropRecViewAdp adapter;
    private Utils utils;
    private ArrayList<MovieItem> wantToWatchList = new ArrayList<>();

    private DatabaseHelper databaseHelper;
    private Cursor cursor;
    private SQLiteDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mylist, container, false);


        adapter = new SmallBackDropRecViewAdp(getContext());
        utils = new Utils(getContext());

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecItemDecorator(12, 12, 12, 12));

        DatabaseAsyncTask databaseAsyncTask = new DatabaseAsyncTask();
        databaseAsyncTask.execute();


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        database.close();
    }

    private class DatabaseAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adapter.clearItems();

            databaseHelper = new DatabaseHelper(getContext());
            database = databaseHelper.getReadableDatabase();
        }

        @Override
        protected String doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: called");

            try {
                cursor = database.query("movies", null, null, null, null, null
                        , null);

                if (cursor.moveToFirst()) {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        MovieItem movieItem = new MovieItem();
                        for (int j = 0; j < cursor.getColumnCount(); j++) {
                            if (cursor.getColumnName(j).equals("movie")) {

                                Gson gson = new Gson();
                                movieItem = gson.fromJson(cursor.getString(j), MovieItem.class);


                            }

                        }
                        Log.d(TAG, "doInBackground: movieItem " + movieItem.toString());
                        wantToWatchList.add(movieItem);
                        cursor.moveToNext();
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            adapter.setItems(wantToWatchList);
        }
    }
}
