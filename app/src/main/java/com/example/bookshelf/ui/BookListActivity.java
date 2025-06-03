package com.example.bookshelf.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookshelf.R;
import com.example.bookshelf.data.model.Book;

import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {

    private final String TAG = "BookListActivity";
    private BookListViewModel viewModel;
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new BookAdapter(null,this,
                this::onBookClick,
                this::onFavoriteClick);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(BookListViewModel.class);


        // Observe data changes
        observeViewModel();
    }

    private void onBookClick(Book book) {
        Log.d(TAG, "Book Clicked");
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra("BOOK_ID", book.getId());
        startActivity(intent);
    }

    private void onFavoriteClick(Book book) {
        viewModel.toggleFavorite(book.getId(), !book.isFavorite());

        Toast.makeText(this,
                book.isFavorite() ? "Removed from favorites" : "Added to favorites",
                Toast.LENGTH_SHORT).show();
    }

    private void observeViewModel() {
        // Observe book list
        viewModel.getBooks().observe(this, books -> {
            if (books != null) {
                adapter.setBooks(books);
                progressBar.setVisibility(View.GONE);
            }
        });

        // Observe loading state
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

        // Observe error messages
        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}