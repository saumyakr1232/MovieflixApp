package com.example.movie2.Database;

public interface DatabaseObserver {
    void onItemAdded();

    void onItemDeleted();

    void onDatabaseChanged();
}
