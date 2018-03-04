package com.example.android.news;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;


/**
 * Created by android on 2018.03.03..
 */

public class openNewsClickListener implements AdapterView.OnItemClickListener {

    private NewsAdapter adapter;
    Context context;

    public openNewsClickListener (NewsAdapter mAdapter, Context mContext){
        adapter = mAdapter;
        context = mContext;
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        News currentNews = adapter.getItem(position);

        String url = currentNews.getWebUrl();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);

    }
}
