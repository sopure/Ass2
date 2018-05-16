package edu.monash.swan.ass2.WeatherInfo;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.monash.swan.ass2.Common.NetworkUtil;

public class WeatherUtil {

    private static List getForecast(){
        String key = "87413bd9a5614b94b8f14729bf8d26ae";
        String url = "https://free-api.heweather.com/s6/weather/forecast?key=" + key + "&location=auto_ip";
        List<Forecast> forecastList = new ArrayList<>();
        String result = NetworkUtil.SendGet(url);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
        } catch (Exception e) {
            jsonObject = null;
        }
        if(jsonObject!=null) {
            try{
                JSONArray ja = (JSONArray) jsonObject.get("HeWeather6");
                ja = ja.getJSONObject(0).getJSONArray("daily_forecast");

                for(int i = 0; i < ja.length(); i++){
                    JSONObject js = ja.getJSONObject(i);
                    Forecast forecast = new Forecast();
                    forecast.date = js.getString("date");
                    forecast.max = js.getString("tmp_max");
                    forecast.min = js.getString("tmp_min");
                    forecast.cond_txt_d = js.getString("cond_txt_d");
                    forecastList.add(forecast);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return forecastList;
    }

    private static Now getNow(){
        Now now = new Now();
        String key = "87413bd9a5614b94b8f14729bf8d26ae";
        String url = "https://free-api.heweather.com/s6/weather/now?key=" + key + "&location=auto_ip";
        String result = NetworkUtil.SendGet(url);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
        } catch (Exception e) {
            jsonObject = null;
        }
        if(jsonObject!=null) {
            try{
                JSONArray ja = (JSONArray) jsonObject.get("HeWeather6");
                JSONObject js = ja.getJSONObject(0).getJSONObject("now");
                now.cond_txt = js.getString("cond_txt");
                now.tmp = js.getString("tmp");
                now.update = ja.getJSONObject(0).getJSONObject("update").getString("loc");
                now.city = ja.getJSONObject(0).getJSONObject("basic").getString("location");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return now;
    }

    private static AQI getAQI(){
        AQI aqi = null;
        String key = "87413bd9a5614b94b8f14729bf8d26ae";
        String url = "https://free-api.heweather.com/s6/air/now?key=" + key + "&location=auto_ip";
        String result = NetworkUtil.SendGet(url);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
        } catch (Exception e) {
            jsonObject = null;
        }
        if(jsonObject!=null) {
            try{
                aqi = new AQI();
                JSONArray ja = (JSONArray) jsonObject.get("HeWeather6");
                JSONObject js = ja.getJSONObject(0).getJSONObject("air_now_city");
                aqi.aqi = js.getString("aqi");
                aqi.pm25 = js.getString("pm25");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return aqi;
    }

    private static Suggestion getSuggestion(){
        Suggestion suggestion = new Suggestion();
        String key = "87413bd9a5614b94b8f14729bf8d26ae";
        String url = "https://free-api.heweather.com/s6/weather/lifestyle?key=" + key + "&location=auto_ip";
        String result = NetworkUtil.SendGet(url);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
        } catch (Exception e) {
            jsonObject = null;
        }
        if(jsonObject!=null) {
            try{
                JSONArray ja = jsonObject.getJSONArray("HeWeather6").getJSONObject(0).getJSONArray("lifestyle");
                suggestion.comf = ja.getJSONObject(0).getString("txt");
                suggestion.drsg = ja.getJSONObject(1).getString("txt");
                suggestion.sport = ja.getJSONObject(4).getString("txt");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return suggestion;
    }

    public static Weather requestWeather(){
        Weather weather = new Weather();
        weather.forecastList = getForecast();
        weather.now = getNow();
        weather.aqi = getAQI();
        weather.suggestion = getSuggestion();
        return weather;
    }

}
