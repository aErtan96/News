package com.dertsizvebugsiz.news.dataclasses;

import java.util.Date;

public class News {

    public int newsId;
    public String title;
    public String subTitle;
    public String body;
    public String publishDateStr;
    public int siteId;
    public String siteName;
    public String link;

    public News(){

    }

    public News(String title, String body, String date, int siteId) {
        this.title = title;
        this.body = body;
        this.publishDateStr = date;
        this.siteId = siteId;
    }

    public News(int newsId, String title, String subTitle, String body, String date, int siteId, String siteName, String link) {
        this.newsId = newsId;
        this.title = title;
        this.subTitle = subTitle;
        this.body = body;
        this.publishDateStr = date;
        this.siteId = siteId;
        this.siteName = siteName;
        this.link = link;
    }
}
