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
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie2.Adapter.RecItemDecorator;
import com.example.movie2.Adapter.SmallBackDropRecViewAdp;
import com.example.movie2.Database.LocalStorageDb;
import com.example.movie2.Model.MovieItem;
import com.example.movie2.R;
import com.example.movie2.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MyListFragment extends Fragment {
    private static final String TAG = "MyListFragment";

    private RecyclerView recyclerView;
    private FloatingActionButton editListFab;
    private SmallBackDropRecViewAdp adapter;
    private Utils utils;
    private ArrayList<MovieItem> wantToWatchList = new ArrayList<>();

    private RelativeLayout parent;
    private LocalStorageDb localStorageDb;
    private Cursor cursor;
    private SQLiteDatabase database;
    private boolean isSelecting = false;
    private MovieItem lastDeleted;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mylist, container, false);


        adapter = new SmallBackDropRecViewAdp(getContext());
        utils = new Utils(getContext());

        recyclerView = view.findViewById(R.id.recyclerView);
        editListFab = view.findViewById(R.id.editListFab);
        parent = view.findViewById(R.id.parent);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecItemDecorator(12, 12, 12, 12));

        editListFab.setOnClickListener(view12 -> {
            if (!isSelecting) {
                adapter.setSelecting(true);
                isSelecting = true;
                editListFab.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_delete_24));
            } else {
                final ArrayList<MovieItem> copyOfRemovedItems = new ArrayList<>(adapter.getSelectedItems());
                editListFab.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_edit_24));

                adapter.removeSelected();

                Snackbar snackbar = Snackbar
                        .make(parent,
                                "Removed " + copyOfRemovedItems.size() + (copyOfRemovedItems.size() == 1 ? "item." : "items."),
                                Snackbar.LENGTH_LONG)
                        .setAction("UNDO", view1 -> {
                            adapter.addItems(copyOfRemovedItems);
                            for (MovieItem item : copyOfRemovedItems) {
                                localStorageDb.insert(database, item);
                            }
                            copyOfRemovedItems.clear();

                        });

                adapter.getSelectedItems().clear();
                snackbar.show();
                isSelecting = false;

            }
        });

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

            localStorageDb = new LocalStorageDb(getContext());
            database = localStorageDb.getReadableDatabase();
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
                        wantToWatchList.add(0, movieItem);
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

            adapter.updateItems(wantToWatchList);
        }
    }
}
