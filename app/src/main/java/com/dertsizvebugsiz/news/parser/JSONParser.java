package com.dertsizvebugsiz.news.parser;

import android.util.Log;
import com.dertsizvebugsiz.news.dataclasses.Currency;
import com.dertsizvebugsiz.news.dataclasses.News;
import org.json.JSONArray;
import org.json.JSONObject;

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
            news.body = newsJsonObject.getString("body");
            news.subTitle = newsJsonObject.getString("sub_title");
            news.publishDateStr = newsJsonObject.getString("publish_date");
            news.link = newsJsonObject.getString("link");
            return news;
        }
        catch (Exception e){
            Log.e("ERROR","Error occur while parsing singleNewsResponse\n" + e.getMessage());
            return null;
        }
    }

}
