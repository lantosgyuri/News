package com.example.android.news;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.news.SettingsActivity.theme1Key;
import static com.example.android.news.SettingsActivity.theme2Key;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String BaseUrl = "https://content.guardianapis.com/search";

    private static final int THEMA1_LOADER = 1;
    private static final int THEMA2_LOADER = 2;

    private NewsAdapter thema1Adapter, thema2Adapter;
    private ListView thema1ListView, thema2ListView;
    private TextView thema1Titel, thema2Titel;

    private View refreschIcon;


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

        thema1Titel = findViewById(R.id.thema1_titel_text_view);
        thema2Titel = findViewById(R.id.thema2_title_text_view);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        thema1Titel.setText(sharedPref.getString(theme1Key, "You have to give a theme in settings"));
        thema2Titel.setText(sharedPref.getString(theme2Key, "You have to give a theme in settings"));


        refreschIcon = findViewById(R.id.refresh_icon);

        refreschIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            getLoaderManager().restartLoader(THEMA1_LOADER, null, thema1Loader);
            getLoaderManager().restartLoader(THEMA2_LOADER, null, thema2Loader);
            Toast.makeText(getApplicationContext(), "News refreshed", Toast.LENGTH_LONG).show();
            }
        });
    }


    private LoaderManager.LoaderCallbacks<List<News>> thema1Loader =
            new LoaderManager.LoaderCallbacks<List<News>>() {
                @Override
                public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    String thema = sharedPreferences.getString(theme1Key, "");

                    Uri baseUri = Uri.parse(BaseUrl);
                    Uri.Builder uriBuilder = baseUri.buildUpon();

                    uriBuilder.appendQueryParameter("q", thema);
                    uriBuilder.appendQueryParameter("api-key", "test");
                    Log.e(LOG_TAG," url1: " + uriBuilder.toString());

                    return new NewsLoader(MainActivity.this, uriBuilder.toString());
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

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    String thema2 = sharedPreferences.getString(theme2Key, "");

                    Uri baseUri = Uri.parse(BaseUrl);
                    Uri.Builder uriBuilder = baseUri.buildUpon();

                    uriBuilder.appendQueryParameter("q", thema2);
                    uriBuilder.appendQueryParameter("api-key", "test");
                    Log.e(LOG_TAG," url2: " + uriBuilder.toString());

                    return new NewsLoader(MainActivity.this, uriBuilder.toString());
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.favorit_news:
                Intent intent = new Intent(this, FavoritNews.class);
                startActivity(intent);

                return true;

            case R.id.settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);

                return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
