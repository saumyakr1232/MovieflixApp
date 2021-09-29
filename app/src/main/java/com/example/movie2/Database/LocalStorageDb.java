package com.example.movie2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.movie2.Model.MovieItem;
import com.google.gson.Gson;

import java.util.ArrayList;

public class LocalStorageDb extends SQLiteOpenHelper implements DatabaseObservable {
    private static final String TAG = "DatabaseHelper";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movieStore";
    private static final ArrayList<DatabaseObserver> observerArrayList = new ArrayList<>();
    private static LocalStorageDb mLocalStorageDb;

    public LocalStorageDb(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized LocalStorageDb getInstance(Context context) {
        if (mLocalStorageDb == null) {
            mLocalStorageDb = new LocalStorageDb(context.getApplicationContext());
        }

        return mLocalStorageDb;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: Attempting to create database");

        String sqlStatement = "CREATE TABLE movies(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "movie TEXT, UNIQUE(movie));";
        db.execSQL(sqlStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean insert(SQLiteDatabase db, MovieItem movieItem) throws android.database.sqlite.SQLiteConstraintException {
        Log.d(TAG, "insert: attempting to insert movie :" + movieItem.getTitle());

        Gson gson = new Gson();
        String movieJsonString = gson.toJson(movieItem);
        ContentValues contentValues = new ContentValues();

        contentValues.put("movie", movieJsonString);
        try {
            long l = db.insert("movies", null, contentValues);
            if (l == -1) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        notifyItemAdded();
        return true;

    }

    public void delete(SQLiteDatabase db, MovieItem movie) {
        Log.d(TAG, "delete: attemptiog to delete movie at id: " + movie.getTitle());
        Gson gson = new Gson();
        db.delete("movies", "movie=?", new String[]{gson.toJson(movie)});
        notifyItemDeleted();
    }

    @Override
    public void registerDbObserver(DatabaseObserver databaseObserver) {
        if (!observerArrayList.contains(databaseObserver)) {
            observerArrayList.add(databaseObserver);
        }
    }

    @Override
    public void removeDbObserver(DatabaseObserver databaseObserver) {
        observerArrayList.remove(databaseObserver);
    }

    @Override
    public void notifyItemAdded() {
        for (DatabaseObserver databaseObserver : observerArrayList) {
            if (databaseObserver != null) {
                databaseObserver.onItemAdded();
            }
        }
    }

    @Override
    public void notifyItemDeleted() {
        for (DatabaseObserver databaseObserver : observerArrayList) {
            if (databaseObserver != null) {
                databaseObserver.onItemDeleted();
            }
        }
    }

    @Override
    public void notifyDbChanged() {
        Log.d(TAG, "notifyDbChanged: Called");
        for (DatabaseObserver databaseObserver : observerArrayList) {
            if (databaseObserver != null) {
                databaseObserver.onDatabaseChanged();
            }
        }

    }
}