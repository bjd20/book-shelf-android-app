package com.example.bookshelf.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookshelf.BookShelf;
import com.example.bookshelf.R;
import com.example.bookshelf.data.repository.BookRepository;
import com.squareup.picasso.Picasso;

public class BookDetailActivity extends AppCompatActivity {

    private final String TAG = "BookDetailActivity";

    private BookDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView bookImage = findViewById(R.id.book_image);
        TextView title = findViewById(R.id.title);
        TextView author = findViewById(R.id.author);
        TextView description = findViewById(R.id.description);
        RatingBar rating = findViewById(R.id.rating);

        String bookId = getIntent().getStringExtra("BOOK_ID");

        // Getting repository instance (from Application class)
        BookRepository repository = ((BookShelf) getApplication()).getRepository();

        BookDetailViewModelFactory factory = new BookDetailViewModelFactory(repository);
        viewModel = new ViewModelProvider(this,factory).get(BookDetailViewModel.class);

        viewModel.getBook(bookId).observe(this, book -> {
            if (book != null) {
                title.setText(book.getTitle());
                author.setText(book.getAuthor());
                description.setText(book.getDescription());
                rating.setRating(book.getRating());

                Picasso.get()
                        .load(book.getImageUrl())
                        .placeholder(R.drawable.baseline_menu_book_24)
                        .into(bookImage);
            }
        });

    }
}