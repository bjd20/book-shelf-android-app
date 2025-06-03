package com.example.bookshelf.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.bookshelf.data.model.Book;

@Database(entities = {Book.class}, version = 1)
public abstract class BookDatabase extends RoomDatabase {
    public abstract BookDao bookDao();
}
