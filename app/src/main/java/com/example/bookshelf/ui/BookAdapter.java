package com.example.bookshelf.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookshelf.R;
import com.example.bookshelf.data.model.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    private List<Book> books;
    private final Context context;
    private final OnBookClickListener bookClickListener;
    private final OnFavoriteClickListener favoriteClickListener;

    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    public interface OnFavoriteClickListener {
        void onFavoriteClick(Book book);
    }

    public BookAdapter(List<Book> books, Context context, OnBookClickListener bookClickListener, OnFavoriteClickListener favoriteClickListener) {
        this.books = books;
        this.context = context;
        this.bookClickListener = bookClickListener;
        this.favoriteClickListener = favoriteClickListener;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_book, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.MyViewHolder holder, int position) {
        Book book = books.get(position);

        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());

        // Load image
        Picasso.get()
                .load(book.getThumbnailUrl())
                .placeholder(R.drawable.baseline_menu_book_24)
                .error(R.drawable.baseline_info)
                .resize(120, 180)
                .into(holder.thumbnail);

        // Set favorite icon
        int favoriteIcon = book.isFavorite()
                ? R.drawable.heart
                : R.drawable.heart_out;
        holder.favoriteButton.setImageResource(favoriteIcon);

        // Click listeners
        holder.itemView.setOnClickListener(v -> bookClickListener.onBookClick(book));
        holder.favoriteButton.setOnClickListener(v -> favoriteClickListener.onFavoriteClick(book));
    }

    @Override
    public int getItemCount() {
        return books != null ? books.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView thumbnail;
        TextView title;
        TextView author;
        ImageView favoriteButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
        }
    }
}
