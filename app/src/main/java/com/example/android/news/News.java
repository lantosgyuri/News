package com.example.android.news;

/**
 * Created by android on 2018.03.03..
 */

public class News {

    private String Title;
    private String Date;
    private String WebUrl;

    public News(String mtitle, String mdate, String mwebUrl ){
        Title = mtitle;
        Date = mdate;
        WebUrl = mwebUrl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String mTitle) {
        this.Title = Title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String mDate) {
        this.Date = Date;
    }

    public String getWebUrl() {
        return WebUrl;
    }

    public void setWebUrl(String WebUrl) {
        this.WebUrl = WebUrl;
    }
}
