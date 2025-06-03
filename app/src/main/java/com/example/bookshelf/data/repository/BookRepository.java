package com.example.bookshelf.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.bookshelf.BookShelf;
import com.example.bookshelf.data.BookDao;
import com.example.bookshelf.data.BookDatabase;
import com.example.bookshelf.data.model.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Executors;

public class BookRepository {
    private BookDao bookDao;
    private final Context context;

    private final String TAG = "BookRepository";


    public BookRepository(Context context, BookDatabase bookDatabase) {
        this.context = context.getApplicationContext();
        this.bookDao = bookDatabase.bookDao();
        loadInitialData();
    }

    private void loadInitialData() {

        Executors.newSingleThreadExecutor().execute(() -> {
            if (bookDao.getAllBooks().getValue() == null || bookDao.getAllBooks().getValue().isEmpty()) {
                // Load from JSON only if DB is empty
                String json = loadJSONFromAsset();
                Log.d(TAG, "Loaded: "+ json);

                List<Book> books = new Gson().fromJson(json, new TypeToken<List<Book>>(){}.getType());
                bookDao.insertAll(books);

                Log.d(TAG, "Loaded Dao: "+ bookDao);

            }
        });
    }


    private String loadJSONFromAsset() {

        Log.d(TAG, "loading JSONFromAsset");

        try (InputStream is = context.getAssets().open("books.json")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "[]";
        }
    }

    public LiveData<List<Book>> getAllBooks() {
        return bookDao.getAllBooks();
    }

    public LiveData<Book> getBookById(String bookId) {
        return bookDao.getBookById(bookId);
    }

    public void toggleFavorite(String bookId, boolean isFavorite) {
        Executors.newSingleThreadExecutor().execute(() -> {
            bookDao.updateFavorite(bookId, isFavorite);
        });
    }
}
