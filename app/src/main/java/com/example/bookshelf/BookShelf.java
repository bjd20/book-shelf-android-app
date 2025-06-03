package com.example.bookshelf;
import android.app.Application;
import android.util.Log;

import androidx.room.Room;

import com.example.bookshelf.data.BookDatabase;

public class BookShelf extends Application {

    private static BookDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("App-BookShelf", "Application class initialized");

        // Initializing Room Database
        database = Room.databaseBuilder(this, BookDatabase.class, "book-db")
                .fallbackToDestructiveMigration()
                .build();
    }

    public static BookDatabase getDatabase() {
        return database;
    }
}