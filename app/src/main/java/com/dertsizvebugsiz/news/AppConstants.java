package com.dertsizvebugsiz.news;

import com.dertsizvebugsiz.news.dataclasses.News;
import androidx.annotation.Nullable;

public class AppConstants {

    public final static String COINS_API_URL = "https://api.coincap.io/v2/assets/";
    private final static String SINGLE_NEW_URL = "https://bcnews.xyz/get_single_new.php?";
    private final static String NEWS_FEED_URL = "https://bcnews.xyz/get_news_feed.php?";
    private final static String LAST_UPLOADED_NEWS_URL = "https://bcnews.xyz/get_last_uploaded_news.php?";
    private final static String UNREAD_NEWS_COUNT_URL = "https://bcnews.xyz/get_unread_news_count.php?";
    private final static String SITE_IMAGES_URL = "https://bcnews.xyz/images-small/site_";
    public final static String SUMMARY_FEEDBACK_URL = "https://bcnews.xyz/send_news_feedback.php";
    public final static String SITES_UPDATE_URL = "https://bcnews.xyz/get_sites.php";

    public final static String CURRENCY_REQUEST_TAG = "currency_request";
    public final static String SINGLE_NEW_REQUEST_TAG = "single_new_request";
    public final static String NEWS_FEED_REQUEST_TAG = "news_feed_request";
    public final static String UNREAD_NEWS_COUNT_REQUEST_TAG = "unread_news_count_request";
    public final static String LAST_UPLOADED_NEWS_REQUEST_TAG = "last_uploaded_news_request";
    public final static String SUMMARY_FEEDBACK_TAG = "summary_feedback";
    public final static String SITES_REQUEST_TAG = "sites_request";


    public final static String ARTICLE_FRAGMENT_TAG = "article_fragment";

    public static String getSingleNewUrl(int newsId){
        return SINGLE_NEW_URL + "id=" + newsId;
    }

    public static String getNewsFeedUrl(int limit, News lastPulledNews, int[] disabledSiteIds){
        String url = NEWS_FEED_URL;

        if(lastPulledNews == null)
            url += "limit=" + limit + "&last_news_id=" + 0 + "&last_news_date=" + "2050-01-01";
        else
            url += "limit=" + limit + "&last_news_id=" + lastPulledNews.newsId + "&last_news_date=" + lastPulledNews.getPublishDatePart();

        if(disabledSiteIds.length != 0)
            url += convertDisabledSitesIdsToParameter(disabledSiteIds);

        return url;
    }

    public static String getLastUploadedNewsUrl(News mostRecentNews, int[] disabledSiteIds){
        String url = LAST_UPLOADED_NEWS_URL + "first_news_id=" + mostRecentNews.newsId + "&first_news_date=" + mostRecentNews.getPublishDatePart();
        if(disabledSiteIds.length != 0)
            url += convertDisabledSitesIdsToParameter(disabledSiteIds);
        return url;
    }

    public static String getUnreadNewsCountUrl(@Nullable News lastPulledNews, int[] disabledSiteIds){
        String url = UNREAD_NEWS_COUNT_URL;
        if(lastPulledNews == null)
            url += "last_pulled_news_id=" + Integer.MAX_VALUE + "&last_pulled_news_date=" + "2050-01-01";
        else
            url += "last_pulled_news_id=" + lastPulledNews.newsId + "&last_pulled_news_date=" + lastPulledNews.getPublishDatePart();

        if(disabledSiteIds.length != 0)
            url += convertDisabledSitesIdsToParameter(disabledSiteIds);

        return url;
    }

    public static String getSiteImageUrl(int siteId) {
        return SITE_IMAGES_URL + siteId  + ".png";
    }

    private static String convertDisabledSitesIdsToParameter(int[] disabledSiteIds){
        StringBuilder stringBuilder = new StringBuilder("&disabled_sites=");
        for(int i = 0; i < disabledSiteIds.length; i++){
            stringBuilder.append(disabledSiteIds[i]);
            if(i != disabledSiteIds.length - 1){
                stringBuilder.append("-");
            }
        }
        return stringBuilder.toString();
    }

}
