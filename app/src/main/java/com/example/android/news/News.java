package com.example.android.news;

/**
 * Created by android on 2018.03.03..
 */

public class News {

    private String mTitle;
    private String mSectionName;
    private String mDate;
    private String mWebUrl;
    private String mContributor;

    public News(String title, String sectionName, String date, String contributor, String webUrl) {
        mTitle = title;
        mSectionName = sectionName;
        mDate = date;
        mWebUrl = webUrl;
        mContributor = contributor;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getDate() {
        return mDate;
    }

    public String getContributor() { return mContributor;}

    public String getWebUrl() {
        return mWebUrl;
    }

}


