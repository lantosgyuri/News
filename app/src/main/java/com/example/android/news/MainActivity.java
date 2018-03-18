package com.example.android.news;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String BaseUrl = "https://content.guardianapis.com/search";

    private static final int THEMA1_LOADER = 1;
    private static final int THEMA2_LOADER = 2;

    private NewsAdapter thema1Adapter, thema2Adapter;
    private ListView thema1ListView;
    private ListView thema2ListView;
    private TextView thema1Titel;
    private TextView thema2Titel;
    private TextView emptyView;

    private View refreschIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //set up the UI
        emptyView = findViewById(R.id.empty_view);

        thema1ListView = findViewById(R.id.thema1_list_view);
        thema1Adapter = new NewsAdapter(this, new ArrayList<News>());
        thema1ListView.setAdapter(thema1Adapter);
        thema1ListView.setOnItemClickListener(new openNewsClickListener(thema1Adapter, this));
        thema1ListView.setEmptyView(emptyView);

        thema2ListView = findViewById(R.id.thema2_list_view);
        thema2Adapter = new NewsAdapter(this, new ArrayList<News>());
        thema2ListView.setAdapter(thema2Adapter);
        thema2ListView.setOnItemClickListener(new openNewsClickListener(thema2Adapter, this));
        thema2ListView.setEmptyView(emptyView);

        thema1Titel = findViewById(R.id.thema1_titel_text_view);
        thema2Titel = findViewById(R.id.thema2_title_text_view);

        //get the Themas what user have set
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        thema1Titel.setText(sharedPref.getString(theme1Key, "You have to give a theme in settings"));
        thema2Titel.setText(sharedPref.getString(theme2Key, "You have to give a theme in settings"));


        refreschIcon = findViewById(R.id.refresh_icon);

        //restart the loaders on button click
        refreschIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            getLoaderManager().restartLoader(THEMA1_LOADER, null, MainActivity.this);
            getLoaderManager().restartLoader(THEMA2_LOADER, null, MainActivity.this);
            Toast.makeText(getApplicationContext(), "News refreshed", Toast.LENGTH_LONG).show();
            }
        });


        //checking internet connection
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            //kick of the Loaders
            getLoaderManager().initLoader(THEMA1_LOADER, null, this);
            getLoaderManager().initLoader(THEMA2_LOADER, null, this);

        } else {
            // Update empty state with no connection error message
            emptyView.setText("There is no internet Connection");
        }


    }



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

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle bundle) {
        switch (id){
            case THEMA1_LOADER:
                //set the URI
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                String thema = sharedPreferences.getString(theme1Key, "");

                Uri baseUri = Uri.parse(BaseUrl);
                Uri.Builder uriBuilder = baseUri.buildUpon();
                uriBuilder.appendQueryParameter("q", thema);
                uriBuilder.appendQueryParameter("show-tags", "contributor");
                uriBuilder.appendQueryParameter("api-key", "test");
                Log.e(LOG_TAG," url1: " + uriBuilder.toString());

                return new NewsLoader(MainActivity.this, uriBuilder.toString());

            case THEMA2_LOADER:
                //set the URI
                SharedPreferences sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                String thema2 = sharedPreferences2.getString(theme2Key, "");

                Uri baseUri2 = Uri.parse(BaseUrl);
                Uri.Builder uriBuilder2 = baseUri2.buildUpon();

                uriBuilder2.appendQueryParameter("q", thema2);
                uriBuilder2.appendQueryParameter("show-tags", "contributor");
                uriBuilder2.appendQueryParameter("api-key", "test");
                Log.e(LOG_TAG," url2: " + uriBuilder2.toString());

                return new NewsLoader(MainActivity.this, uriBuilder2.toString());

        }
        return null;

    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {

        switch (loader.getId()){

            case THEMA1_LOADER:
                thema1Adapter.clear();
                thema1Adapter.addAll(news);
                break;

            case THEMA2_LOADER:
                thema2Adapter.clear();
                thema2Adapter.addAll(news);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

        switch (loader.getId()){

            case THEMA1_LOADER:
                thema1Adapter.clear();
                break;

            case THEMA2_LOADER:
                thema2Adapter.clear();
                break;

        }

    }
}
