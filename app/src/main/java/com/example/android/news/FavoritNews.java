package com.example.android.news;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.android.news.Data.FavoritNewsContract.NewsContractEntry;

import com.example.android.news.Data.FavoritNewsDbHelper;
import com.example.android.news.Data.NewsCursorAdapter;

/**
 * Created by android on 2018.03.04..
 */

public class FavoritNews extends AppCompatActivity {

    private static final String LOG_TAG= FavoritNews.class.getSimpleName();
    SQLiteDatabase db;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorit_news);

        listView = findViewById(R.id.favorit_news_list_view);

        FavoritNewsDbHelper helper = new FavoritNewsDbHelper(this);

        db = helper.getReadableDatabase();

        Cursor cursor = db.query(NewsContractEntry.TABLE_NAME, null, null, null, null, null, null);

        NewsCursorAdapter cursorAdapter = new NewsCursorAdapter(this, cursor);

        listView.setAdapter(cursorAdapter);

    }



}
