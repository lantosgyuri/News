package com.example.android.news;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.news.Data.FavoritNewsDbHelper;
import com.example.android.news.Data.FavoritNewsContract.NewsContractEntry;

import java.util.List;

/**
 * Created by android on 2018.03.03..
 */

public class NewsAdapter extends ArrayAdapter<News> {

    private static final String LOG_TAG = NewsAdapter.class.getSimpleName();

    public NewsAdapter(@NonNull Context context, @NonNull List<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if(listItemView == null){

            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        final News currentNews = getItem(position);

        TextView titleText = listItemView.findViewById(R.id.list_item_title_text);
        titleText.setText(currentNews.getTitle());

        titleText.setTextColor(SettingsActivity.getTextColor(getContext()));

        TextView sectionText = listItemView.findViewById(R.id.list_item_section_name);
        sectionText.setText(currentNews.getSectionName());

        TextView dateText = listItemView.findViewById(R.id.list_item_publication_date);
        dateText.setText(currentNews.getDate());

        TextView authorText = listItemView.findViewById(R.id.list_item_contributor);
        authorText.setText(currentNews.getContributor());

        View saveIcon = listItemView.findViewById(R.id.list_item_save_icon);

        //save it in the Favorite news Database
        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoritNewsDbHelper helper = new FavoritNewsDbHelper(getContext());
                SQLiteDatabase db = helper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(NewsContractEntry.COLUMN_NEWS_TITEL, currentNews.getTitle());
                values.put(NewsContractEntry.COLUMN_NEWS_WEBURL, currentNews.getWebUrl());

                db.insert(NewsContractEntry.TABLE_NAME, null, values );
                Log.e(LOG_TAG,"News was inserted " +values);
                Toast.makeText(getContext(), "News inserted", Toast.LENGTH_LONG).show();
            }
        });

        return listItemView;
    }

}
