package com.example.android.news.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.android.news.Data.FavoritNewsContract.NewsContractEntry;


/**
 * Created by android on 2018.03.04..
 */

public class FavoritNewsDbHelper extends SQLiteOpenHelper {


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

}
