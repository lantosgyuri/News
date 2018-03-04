package com.example.android.news.Data;

import android.provider.BaseColumns;

/**
 * Created by android on 2018.03.04..
 */

public class FavoritNewsContract {

    public FavoritNewsContract () {}

    public static final class NewsContractEntry implements BaseColumns {

        public static final String TABLE_NAME = "favoritNews";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NEWS_TITEL = "titel";
        public static final String COLUMN_NEWS_WEBURL = "weburl";


    }

}
