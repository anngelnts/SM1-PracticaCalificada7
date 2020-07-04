package com.desarrollo.practicacalificada7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;

import com.desarrollo.practicacalificada7.JSON.JSONDownloader;
import com.desarrollo.practicacalificada7.Models.BookModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String END_POINT =  "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String START_INDEX = "&startIndex=";
    private static final String MAX_RESULTS = "&maxResults=";
    private int startIndex = 0;
    private int maxResults = 40;
    private String queryCurrent = "";

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AsyncBook("javacript");
        RecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                AsyncBook(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //AsyncBook(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void AsyncBook(String query) {
        queryCurrent = query;
        JSONDownloader jsonDownloader = new JSONDownloader(this);
        jsonDownloader.execute(END_POINT+query+MAX_RESULTS+maxResults);
    }

    private void AsyncBookPagination() {
        JSONDownloader jsonDownloader = new JSONDownloader(this);
        jsonDownloader.execute(END_POINT+queryCurrent+START_INDEX+startIndex+maxResults);
    }

    private void RecyclerView() {
        recyclerView = findViewById(R.id.books_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    public void setBooks(ArrayList<BookModel> books) {
        BookAdapter bookAdapter = new BookAdapter(books);
        recyclerView.setAdapter(bookAdapter);
    }
}
