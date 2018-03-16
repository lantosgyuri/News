package com.example.android.news;

/**
 * Created by android on 2018.03.03..
 */

public class News {

    private String mTitle;
    private String mSectionName;
    private String mWebUrl;

    public News(String title, String sectionName, String webUrl ){
        mTitle = title;
        mSectionName = sectionName;
        mWebUrl = webUrl;
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

    public void setSectionName(String sectionName) {
        mSectionName = sectionName;
    }

    public String getWebUrl() {
        return mWebUrl;
    }

    public void setWebUrl(String webUrl) {
        mWebUrl = webUrl;
    }
}
