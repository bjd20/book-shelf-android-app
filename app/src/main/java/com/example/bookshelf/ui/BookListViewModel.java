package com.example.bookshelf.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookshelf.data.model.Book;
import com.example.bookshelf.data.repository.BookRepository;

import java.util.List;

public class BookListViewModel extends AndroidViewModel {

    private final String TAG = "BookListViewModel";
    private final BookRepository repository;

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public BookListViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "Initiated");
        this.repository = new BookRepository(application.getApplicationContext());
        loadBooks();
    }


    public LiveData<List<Book>> getBooks() {
        return repository.getAllBooks();
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    private void loadBooks() {
        Log.d(TAG, "Loading Books...");

        isLoading.setValue(true);
        repository.getAllBooks().observeForever(books -> {
            isLoading.setValue(false);
            if (books == null || books.isEmpty()) {
                errorMessage.setValue("No books available");
                Log.d(TAG, "No books available");
            }
            else{
                Log.d(TAG, "Books: "+ books.toString());
            }
        });
    }

    public void toggleFavorite(String bookId, boolean isFavorite) {
        repository.toggleFavorite(bookId, isFavorite);
    }

}
