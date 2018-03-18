package com.example.android.news;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2018.03.03..
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String mUrl;
    // set the basic date string in case the date is not available.
    private String date = "Date not available";
    //set the contributor in case if the data is not available
    private String contributor = "Contributor not available";
    private static final String LOG_TAG = NewsLoader.class.getSimpleName();
    private static final String RESPONSE = "response";
    private static final String RESULTS = "results";
    private static final String WEBTITLE = "webTitle";
    private static final String SECTIONNAME = "sectionName";
    private static final String DATE ="webPublicationDate";
    private static final String WEBURL = "webUrl";

    public NewsLoader(Context context, String Url) {
        super(context);
        mUrl = Url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) return null;

        return fetchData(mUrl);
    }

    private List<News> fetchData(String mUrl) {
        URL url;
        String baseJson = "";

        url = createUrl(mUrl);
        try {
            baseJson = makeHttpConnection(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem with closing the inputStream or disconnection");
        }

        return extractJsonToList(baseJson);
    }

    private List<News> extractJsonToList(String baseJson) {

        List<News> newsList = new ArrayList<>();

        try {
            JSONObject baseJsonObject = new JSONObject(baseJson);
            JSONObject response = baseJsonObject.getJSONObject(RESPONSE);
            JSONArray results = response.getJSONArray(RESULTS);

             for (int i = 0; i<results.length();i++){
                 JSONObject currentJson = results.getJSONObject(i);

                 String title = currentJson.getString(WEBTITLE);
                 String sectionName = currentJson.getString(SECTIONNAME);

                 //check the date
                 if (currentJson.has(DATE)){
                     String fullDate = currentJson.getString(DATE);
                     // make the date nicer. The date format is good, but I need just the first 10 character
                     date = fullDate.substring(0,9);
                 }

                 //check the author
                 JSONArray tags = currentJson.getJSONArray("tags");
                 if (tags.length() != 0) {
                     JSONObject authorTag = tags.getJSONObject(0);
                     contributor= authorTag.getString(WEBTITLE);
                 }

                 String url = currentJson.getString(WEBURL);

                 News moreNews = new News(title, sectionName, date, contributor, url);

                 newsList.add(moreNews);
             }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem with making the JSon from String");
        }

        return newsList;
    }


    private String makeHttpConnection(URL url) throws IOException{
        String baseJson = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(1000);
            httpURLConnection.setConnectTimeout(1500);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                baseJson = readFromStream(inputStream);
            }

        }catch (IOException e){
            Log.e(LOG_TAG, "problem with making the HTTP CONNECTION");

        }finally{
            if (httpURLConnection != null) httpURLConnection.disconnect();
            if (inputStream != null) inputStream.close();
        }

        return baseJson;
    }

    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if(inputStream != null){
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();

            while ( line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }

        } else Log.e(LOG_TAG, "InputStream is null");

        return output.toString();
    }

    private URL createUrl(String mUrl) {
        URL url= null;

        try {
            url = new URL(mUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG,"PRoblem with making the URL from String");
        }

        return url;
    }


}
