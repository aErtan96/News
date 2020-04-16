package com.dertsizvebugsiz.news.data;

import java.util.Date;

public class News {

    public String title;
    public String body;
    public Date date;
    public int siteId;

    public News(String title, String body, Date date, int siteId) {
        this.title = title;
        this.body = body;
        this.date = date;
        this.siteId = siteId;
    }
}
