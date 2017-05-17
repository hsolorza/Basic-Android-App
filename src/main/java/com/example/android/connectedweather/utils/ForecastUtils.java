package com.example.android.connectedweather.utils;

/**
 * Created by soloh on 5/8/2017.
 */

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class ForecastUtils {


    public static class SearchResult implements Serializable {
        public static final String EXTRA_SEARCH_RESULT = "ForecastUtils.SearchResult";
        public String date;
        public String description;
        public String tempurature;
    }

    public static String buildWeatherSearchURL(){
        URL url = null;
        try {
            url = new URL("http://api.openweathermap.org/data/2.5/forecast?q=corvallis,us&units=imperial&appid=3c777e7e93e5fab70984d9b804058c40");
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url.toString();
    }

    public static ArrayList<SearchResult> parseForecastSearchResultsJSON(String searchResultsJSON) {

        try {
            JSONObject searchResultsObj = new JSONObject(searchResultsJSON);
            JSONArray searchResultsItems = searchResultsObj.getJSONArray("list");

            ArrayList<SearchResult> searchResultsList = new ArrayList<>();
            for (int i = 0; i < searchResultsItems.length(); i++) {
                SearchResult searchResult = new SearchResult();
                JSONObject searchResultItem = searchResultsItems.getJSONObject(i);

                searchResult.date = searchResultItem.getString("dt");

                JSONArray descriptionArray = searchResultItem.getJSONArray("weather");
                searchResult.description = descriptionArray.getJSONObject(0).getString("main");

                searchResult.tempurature = searchResultItem.getJSONObject("main").getString("temp");
                searchResultsList.add(searchResult);
            }
            return searchResultsList;
        } catch (JSONException e) {
            return null;
        }
    }
}