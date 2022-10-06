package com.kasimkartal866.mybookmedia.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kasimkartal866.mybookmedia.common.G;
import com.kasimkartal866.mybookmedia.db.Book;
import com.kasimkartal866.mybookmedia.R;
import com.kasimkartal866.mybookmedia.db.RoomExecutor;
import com.kasimkartal866.mybookmedia.adapters.Adapter;

import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends AppCompatActivity {
    Adapter adapter;
    ArrayList<Book> bookArrayList;
    RecyclerView recyclerView;
    int userId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        if (getIntent().hasExtra(G.BOOKS_LIST_INTENT_KEY)) {
            userId = getIntent().getIntExtra(G.BOOKS_LIST_INTENT_KEY, -1);
        }

        adapter = new Adapter();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onResume() {
        List<Book> books = RoomExecutor.getInstance(this).getBooksByUser(userId);
        adapter.submitList(books);
        recyclerView.setAdapter(adapter);
        super.onResume();
    }
}