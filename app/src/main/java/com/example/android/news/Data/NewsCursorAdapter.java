package com.example.android.news.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.news.R;

import com.example.android.news.Data.FavoritNewsContract.NewsContractEntry;

/**
 * Created by android on 2018.03.04..
 */

public class NewsCursorAdapter extends CursorAdapter {

    SQLiteDatabase db;



    public NewsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        TextView titleText = view.findViewById(R.id.list_item_title_text);
        TextView dateText = view.findViewById(R.id.list_item_publication_date);
        ImageView deleteIcon = view.findViewById(R.id.list_item_save_icon);

        int titleColumnIndex = cursor.getColumnIndex(NewsContractEntry.COLUMN_NEWS_TITEL);
        String title = cursor.getString(titleColumnIndex);

        int idIndex = cursor.getColumnIndex(NewsContractEntry._ID);
        final int index = cursor.getInt(idIndex);

        titleText.setText(title);
        dateText.setText("");
        deleteIcon.setImageResource(R.drawable.ic_delete);

        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoritNewsDbHelper helper = new FavoritNewsDbHelper(context);
                db = helper.getWritableDatabase();


                String whereClause = "_id=?";
                String[] whereArgs = new String[] { String.valueOf(index) };
                db.delete(NewsContractEntry.TABLE_NAME, whereClause, whereArgs);

                Toast.makeText(context, "News deleted", Toast.LENGTH_LONG).show();
            }
        });

    }
}
