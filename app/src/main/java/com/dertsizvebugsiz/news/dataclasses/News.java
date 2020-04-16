package com.dertsizvebugsiz.news.dataclasses;

import java.util.Date;

public class News {

    public int newsId;
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
