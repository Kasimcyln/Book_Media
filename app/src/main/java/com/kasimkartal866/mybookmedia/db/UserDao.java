package com.kasimkartal866.mybookmedia.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface UserDao {
    @Query("SELECT * FROM users WHERE email =(:email) and password = (:password)")
    User checkUserPass(String email , String password);

    @Insert
    void addUser(User user);

    @Delete
    void deleteUser(User user);

    @Delete
    void deleteBook(Book book);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Insert
    void addBook(Book book);
    
    @Query("SELECT * FROM Book")
    List<Book> getAllBooks();

    @Query("SELECT * FROM Book where userId = :userId")
    List<Book> getMyBooks(int userId);
}