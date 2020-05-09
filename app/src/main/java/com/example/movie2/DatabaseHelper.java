package com.example.movie2;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.movie2.Model.MovieItems;
import com.google.gson.Gson;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movieStore";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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


    public boolean insert(SQLiteDatabase db, MovieItems movieItem) throws android.database.sqlite.SQLiteConstraintException {
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
        return true;

    }

    public void delete(SQLiteDatabase db, MovieItems movie) {
        Log.d(TAG, "delete: attemptiog to delete movie at id: " + movie.getTitle());
        Gson gson = new Gson();
        db.delete("movies", "movie=?", new String[]{gson.toJson(movie)});
    }
}