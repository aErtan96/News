package com.dertsizvebugsiz.news.parser;

import android.util.Log;
import com.dertsizvebugsiz.news.dataclasses.Currency;
import com.dertsizvebugsiz.news.dataclasses.News;
import com.dertsizvebugsiz.news.dataclasses.Site;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.LinkedHashMap;

public class JSONParser {

    public static Currency[] parseCurrencyApiResponse(JSONObject response){

        try{
            JSONArray currenciesJsonArray = response.getJSONArray("data");
            Currency[] currencies = new Currency[currenciesJsonArray.length()];
            for(int i = 0; i < currencies.length; i++){
                JSONObject currencyJson = currenciesJsonArray.getJSONObject(i);
                Currency currency = new Currency();
                currency.name = currencyJson.getString("name");
                currency.symbol = currencyJson.getString("symbol");
                currency.price = currencyJson.getDouble("priceUsd");
                currency.dailyChangePercent = currencyJson.getDouble("changePercent24Hr");
                currencies[i] = currency;
            }
            return currencies;
        }
        catch (Exception e){
            return null;
        }

    }

    public static News[] parseNewsFeedResponse(JSONArray jsonArray){
        try{
            News[] feed = new News[jsonArray.length()];
            for(int i = 0; i < feed.length; i++){
                JSONObject singleNewsJson = jsonArray.getJSONObject(i);
                News singleNews = new News();
                singleNews.newsId = singleNewsJson.getInt("id");
                singleNews.title = singleNewsJson.getString("title");
                singleNews.publishDateStr = singleNewsJson.getString("publish_date");
                singleNews.siteId = singleNewsJson.getInt("site_id");
                singleNews.siteName = singleNewsJson.getString("name");
                singleNews.link = singleNewsJson.getString("link");
                feed[i] = singleNews;
            }
            return feed;
        }
        catch (Exception e){
            Log.e("ERROR","Error occur while parsing news feed\n" + e.getMessage());
            return null;
        }
    }

    public static News parseSingleNewsResponse(JSONArray response){
        try{
            JSONObject newsJsonObject = response.getJSONObject(0);
            News news = new News();
            news.newsId = newsJsonObject.getInt("id");
            news.title = newsJsonObject.getString("title");
            news.summary = newsJsonObject.getString("summary");
            news.subTitle = newsJsonObject.getString("sub_title");
            news.publishDateStr = newsJsonObject.getString("publish_date");
            news.link = newsJsonObject.getString("link");
            news.siteId = newsJsonObject.getInt("site_id");
            news.siteName = newsJsonObject.getString("name");
            return news;
        }
        catch (Exception e){
            Log.e("ERROR","Error occur while parsing singleNewsResponse\n" + e.getMessage());
            return null;
        }
    }

    public static LinkedHashMap<Integer, Site> parseSitesResponse(JSONArray response){
        try{
            LinkedHashMap<Integer, Site> sites = new LinkedHashMap<>();
            for(int i = 0; i < response.length(); i++){
                JSONObject singleSiteJson = response.getJSONObject(i);
                Site site = new Site();
                site.siteId = singleSiteJson.getInt("id");
                site.name = singleSiteJson.getString("name");
                site.homeUrl = singleSiteJson.getString("home_url");
                sites.put(site.siteId, site);
            }
            Log.d("DEBUG", "json parser size: " + sites.size());
            return sites;
        }
        catch (Exception e){
            Log.e("ERROR","Error occur while parsing news feed\n" + e.getMessage());
            return null;
        }
    }

}
