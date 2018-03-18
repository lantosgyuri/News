package com.example.android.news;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;


/*I have two ListView So, I have made a separate class for the OnItemClick listener
*to make the code shorter.
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

        //check there is a browser or not
        PackageManager packageManager = context.getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            context.startActivity(intent);

        } else {
            Log.d("Click Listener:", "No Intent available to handle action");
        }
    }
}
