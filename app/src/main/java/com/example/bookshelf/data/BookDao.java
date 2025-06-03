package com.example.bookshelf.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.bookshelf.data.model.Book;

import java.util.List;

@Dao
public interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBook(Book book);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Book> books);  // Added this method

    @Query("SELECT * FROM books")
    LiveData<List<Book>> getAllBooks();

    @Query("UPDATE books SET isFavorite = :isFavorite WHERE id = :bookId")
    void updateFavorite(String bookId, boolean isFavorite);

    @Query("SELECT * FROM books WHERE id = :bookId")
    LiveData<Book> getBookById(String bookId);


}
