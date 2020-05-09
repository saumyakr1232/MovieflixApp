package com.example.movie2;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.movie2.Model.MovieItems;

import java.util.ArrayList;

public class MyAsyncTask extends AsyncTask<Void, Integer, ArrayList<MovieItems>> {
    private static final String TAG = "MyAsyncTask";
    private Context context;
    private MovieItemAdapter adapter;
    private Utils utils;
    private int flag;

    public MyAsyncTask(MovieItemAdapter adapter, Context context, int flag) {
        this.context = context;
        this.adapter = adapter;
        this.flag = flag;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        utils = new Utils(context);
    }

    @Override
    protected ArrayList<MovieItems> doInBackground(Void... voids) {
        Log.d(TAG, "doInBackground: called");
        ArrayList<MovieItems> movieItems = new ArrayList<>();
        switch (flag) {
            case 1:
                movieItems = utils.getAllItems();
                break;
            case 2:
                movieItems = utils.getNewItems();
                break;
            case 3:
                movieItems = utils.getTrendingItems();
                break;
            default:
                movieItems = utils.getAllItems();
                break;
        }
        if (null != movieItems) {
            return movieItems;
        } else {
            Log.d(TAG, "doInBackground: is null");
        }

        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(ArrayList<MovieItems> movieItems) {
        super.onPostExecute(movieItems);
        adapter.setItems(movieItems);
        adapter.notifyDataSetChanged();
    }
}
