package com.dertsizvebugsiz.news.parser;

import com.dertsizvebugsiz.news.dataclasses.Currency;

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

}
