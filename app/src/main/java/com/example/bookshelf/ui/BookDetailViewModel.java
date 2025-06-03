package com.example.bookshelf.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookshelf.data.model.Book;
import com.example.bookshelf.data.repository.BookRepository;

public class BookDetailViewModel extends ViewModel {

    private final String TAG = "BookDetailViewModel";
    private final BookRepository repository;

    public BookDetailViewModel(BookRepository repository) {
        this.repository = repository;
    }

    public LiveData<Book> getBook(String bookId) {
        return repository.getBookById(bookId);
    }
}
