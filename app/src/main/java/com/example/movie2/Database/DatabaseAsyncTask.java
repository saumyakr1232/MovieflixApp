package com.example.movie2.Database;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.movie2.Adapter.SmallBackDropRecViewAdp;
import com.example.movie2.Model.MovieItem;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DatabaseAsyncTask extends AsyncTask<Void, Void, String> {

    private static final int GET_WATCHLIST = 0;
    private static final int DELETE_FROM_WATCHLIST = 1;

    private Context context;
    private DatabaseHelper databaseHelper;
    private Cursor cursor;
    private SQLiteDatabase database;
    private SmallBackDropRecViewAdp adapter;
    private ArrayList<MovieItem> wantToWatchList = new ArrayList<>();
    private int task;
    private MovieItem movie;

    public DatabaseAsyncTask(Context context, SmallBackDropRecViewAdp adapter, int task) {
        this.context = context;
        this.adapter = adapter;
        this.task = task;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getReadableDatabase();
        adapter.clearItems();
    }

    @Override
    protected String doInBackground(Void... voids) {
        if (task == GET_WATCHLIST) {
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
                        wantToWatchList.add(movieItem);
                        cursor.moveToNext();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (task == DELETE_FROM_WATCHLIST) {
            databaseHelper.delete(database, movie);
        }

        return null;

    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        adapter.setItems(wantToWatchList);
    }
}