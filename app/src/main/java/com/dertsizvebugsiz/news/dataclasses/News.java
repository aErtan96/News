package com.dertsizvebugsiz.news.dataclasses;

import java.util.Date;

public class News {

    public int newsId;
    public String title;
    public String subTitle;
    public String summary;
    public String publishDateStr;
    public int siteId;
    public String siteName;
    public String link;

    public News(){

    }

    public News(String title, String summary, String date, int siteId) {
        this.title = title;
        this.summary = summary;
        this.publishDateStr = date;
        this.siteId = siteId;
    }

    public News(int newsId, String title, String subTitle, String summary, String date, int siteId, String siteName, String link) {
        this.newsId = newsId;
        this.title = title;
        this.subTitle = subTitle;
        this.summary = summary;
        this.publishDateStr = date;
        this.siteId = siteId;
        this.siteName = siteName;
        this.link = link;
    }

    public String getPublishDatePart(){
        return publishDateStr.split(" ")[0];
    }

}
