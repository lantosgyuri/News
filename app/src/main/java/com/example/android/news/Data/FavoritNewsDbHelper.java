package com.example.android.news.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.news.Data.FavoritNewsContract.NewsContractEntry;
import com.example.android.news.News;

/**
 * Created by android on 2018.03.04..
 */

public class FavoritNewsDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG=FavoritNewsDbHelper.class.getSimpleName();

    public static final String DATABASE_NAME= "favoritNews";
    public static final int DATABSE_VERSION = 1;

    private SQLiteDatabase db;

    public FavoritNewsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        db = sqLiteDatabase;

        String SQLITE_DB_CREATE_TABLE = "CREATE TABLE " +
                NewsContractEntry.TABLE_NAME + " (" +
                NewsContractEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NewsContractEntry.COLUMN_NEWS_TITEL + " TEXT NOT NULL, " +
                NewsContractEntry.COLUMN_NEWS_WEBURL + " TEXT NOT NULL"  +
                ");";

        db.execSQL(SQLITE_DB_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " +NewsContractEntry.TABLE_NAME);
        onCreate(db);

    }

    public void insertNews(News news) {
        ContentValues values = new ContentValues();
        values.put(NewsContractEntry.COLUMN_NEWS_TITEL, news.getTitle());
        values.put(NewsContractEntry.COLUMN_NEWS_WEBURL, news.getWebUrl());

        db.insert(NewsContractEntry.TABLE_NAME, null, values);
        Log.e(LOG_TAG, "A hir beilesztve " + values);
    }
}
