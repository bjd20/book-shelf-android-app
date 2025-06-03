package com.example.bookshelf;
import android.app.Application;
import android.util.Log;

import androidx.room.Room;

import com.example.bookshelf.data.BookDatabase;
import com.example.bookshelf.data.repository.BookRepository;

public class BookShelf extends Application {

    private static BookDatabase database;
    private BookRepository repository;


    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("App-BookShelf", "Application class initialized");

        // Initializing Room Database
        database = Room.databaseBuilder(this, BookDatabase.class, "book-db")
                .fallbackToDestructiveMigration()
                .build();

        // Initializing repository
        repository = new BookRepository(getApplicationContext(), database);
    }

    public BookRepository getRepository() {
        return repository;
    }

    public static BookDatabase getDatabase() {
        return database;
    }
}