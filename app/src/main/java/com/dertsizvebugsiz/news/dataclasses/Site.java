package com.dertsizvebugsiz.news.dataclasses;

public class Site {

    public int siteId;
    public String name;
    public String homeUrl;
    public boolean isEnabled;

    public Site(int siteId, String name, String homeUrl, boolean isEnabled) {
        this.siteId = siteId;
        this.name = name;
        this.homeUrl = homeUrl;
        this.isEnabled = isEnabled;
    }

    public Site() {

    }
}
