package com.kasimkartal866.mybookmedia.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RoomExecutor {
    private static RoomExecutor instance;
    private final UserDao dao;

    private RoomExecutor(Context context) {
        dao = UserDatabase.getUserDatabase((Context) context).userDao();
    }

    public static RoomExecutor getInstance(Context context) {
        if (instance == null)
            instance = new RoomExecutor(context);
        return instance;
    }

    public User checkUserPass() {
        User user = null;
        CheckUserPass_Async checkUserPassAsync = new CheckUserPass_Async();
        try {
            user = checkUserPassAsync.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void addUser(User user) {
        if (user != null) {
            try {
                new AddUsers_Async().execute(user).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addBook(Book book) {
        if (book != null) {
            new AddBook_Async(book).execute();
        }
    }

    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        try {
            books = new GetBooks_Async().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> getBooksByUser(int userId) {
        List<Book> books = new ArrayList<>();
        try {
            if (userId == -1)
                books = new GetBooks_Async().execute().get();
            else
                books = new GetMyBooks_Async().execute(userId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return books;
    }

    //**********************************************************************************************
    //**********************************************************************************************
    //**********************************************************************************************

    private class CheckUserPass_Async extends AsyncTask<Void, Void, User> {
        @Override
        protected User doInBackground(Void... voids) {
            return dao.checkUserPass("email","password");
        }
    }

    private class Book_Async extends AsyncTask<Void, Void, List<User>> {
        @Override
        protected List<User> doInBackground(Void... voids) {
            return dao.getAllUsers();
        }
    }

    private class AddUsers_Async extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            dao.addUser(users[0]);
            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class AddBook_Async extends AsyncTask<Book, Void, Void> {
        public AddBook_Async(Book book) {
            this.book = book;
        }
        Book book;
        @Override
        protected Void doInBackground(Book... books) {
            dao.addBook(book);
            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetBooks_Async extends AsyncTask<Void, Void, List<Book>> {
        @Override
        protected List<Book> doInBackground(Void... voids) {
            return dao.getAllBooks();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetMyBooks_Async extends AsyncTask<Integer, Void, List<Book>> {
        @Override
        protected List<Book> doInBackground(Integer... userIds) {
            return dao.getMyBooks(userIds[0]);
        }
    }
}