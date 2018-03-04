package com.example.android.news;

import android.app.LoaderManager;
import android.content.Loader;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String BaseUrl = "https://content.guardianapis.com/search?q=soccer&api-key=test";
    private static final String BaseUrl2 = "https://content.guardianapis.com/search?q=money&api-key=test";
    private static final String API_KEY= "&api-key=test";

    private static final int THEMA1_LOADER = 1;
    private static final int THEMA2_LOADER = 2;

    NewsAdapter thema1Adapter, thema2Adapter;
    ListView thema1ListView, thema2ListView;

    View refreschIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        thema1ListView = findViewById(R.id.thema1_list_view);
        thema1Adapter = new NewsAdapter(this, new ArrayList<News>());
        thema1ListView.setAdapter(thema1Adapter);

        thema1ListView.setOnItemClickListener(new openNewsClickListener(thema1Adapter, this));

        thema2ListView = findViewById(R.id.thema2_list_view);
        thema2Adapter = new NewsAdapter(this, new ArrayList<News>());
        thema2ListView.setAdapter(thema2Adapter);

        thema2ListView.setOnItemClickListener(new openNewsClickListener(thema2Adapter, this));

        getLoaderManager().initLoader(THEMA1_LOADER, null, thema1Loader);
        getLoaderManager().initLoader(THEMA2_LOADER, null, thema2Loader);

        refreschIcon = findViewById(R.id.refresh_icon);

        refreschIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            getLoaderManager().restartLoader(THEMA1_LOADER, null, thema1Loader);
            getLoaderManager().restartLoader(THEMA2_LOADER, null, thema2Loader);
            Toast.makeText(getApplicationContext(), "Refresh the news", Toast.LENGTH_LONG).show();
            }
        });
    }


    private LoaderManager.LoaderCallbacks<List<News>> thema1Loader =
            new LoaderManager.LoaderCallbacks<List<News>>() {
                @Override
                public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
                    return new NewsLoader(MainActivity.this, BaseUrl);
                }

                @Override
                public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
                    thema1Adapter.clear();
                    thema1Adapter.addAll(news);
                }

                @Override
                public void onLoaderReset(Loader<List<News>> loader) {
                    thema1Adapter.clear();

                }
            };

    private LoaderManager.LoaderCallbacks<List<News>> thema2Loader =
            new LoaderManager.LoaderCallbacks<List<News>>() {
                @Override
                public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
                    return new NewsLoader(MainActivity.this, BaseUrl2);
                }

                @Override
                public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
                    thema2Adapter.clear();
                    thema2Adapter.addAll(news);

                }

                @Override
                public void onLoaderReset(Loader<List<News>> loader) {

                    thema2Adapter.clear();
                }
            };

}
