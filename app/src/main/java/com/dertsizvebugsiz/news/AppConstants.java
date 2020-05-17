package com.dertsizvebugsiz.news;

public class AppConstants {

    public final static String COINS_API_URL = "https://api.coincap.io/v2/assets/";
    private final static String SINGLE_NEW_URL = "https://bcnews.xyz/get_single_new.php?";
    private final static String NEWS_FEED_URL = "https://bcnews.xyz/get_news_feed.php?";
    private final static String SITE_IMAGES_URL = "https://bcnews.xyz/images/site_";

    public final static String CURRENCY_REQUEST_TAG = "currency_request";
    public final static String SINGLE_NEW_REQUEST_TAG = "single_new_request";
    public final static String NEWS_FEED_REQUEST_TAG = "news_feed_request";


    public final static String ARTICLE_FRAGMENT_TAG = "article_fragment";

    public static String getSingleNewUrl(int newsId){
        return SINGLE_NEW_URL + "id=" + newsId;
    }

    public static String getNewsFeedUrl(int limit, int offset){
        return NEWS_FEED_URL + "limit=" + limit + "&offset=" + offset;
    }

    public static String getSiteImageUrl(int siteId) {
        return SITE_IMAGES_URL + siteId + ".png";
    }
}
