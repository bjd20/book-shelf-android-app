package com.example.bookshelf.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookshelf.data.repository.BookRepository;

public class BookDetailViewModelFactory implements ViewModelProvider.Factory {
    private final BookRepository repository;

    public BookDetailViewModelFactory(BookRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BookDetailViewModel.class)) {
            return (T) new BookDetailViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
