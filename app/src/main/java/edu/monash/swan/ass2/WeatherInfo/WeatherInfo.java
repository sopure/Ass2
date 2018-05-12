package edu.monash.swan.ass2.WeatherInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WeatherInfo {

    private static String SendGet(String url) {
        // 定义一个字符串用来存储网页内容
        String result = "";
        // 定义一个缓冲字符输入流
        BufferedReader in = null;
        try {
            // 将string转成url对象
            URL realUrl = new URL(url);
            // 初始化一个链接到那个url的连接
            URLConnection connection = realUrl.openConnection();
            // 开始实际的连接
            connection.connect();
            // 初始化 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            // 用来临时存储抓取到的每一行的数据
            String line;
            while ((line = in.readLine()) != null) {
                //遍历抓取到的每一行并将其存储到result里面
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static void getWeather(){
        String key = "87413bd9a5614b94b8f14729bf8d26ae";
        String url = "https://free-api.heweather.com/s6/weather/now?key=" + key + "&location=" + Const.CITY;

        String result = SendGet(url);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
        } catch (Exception e) {
            jsonObject = null;
        }
        if(jsonObject!=null) {
            try{
                JSONArray ja = (JSONArray) jsonObject.get("HeWeather6");
                JSONObject js = (JSONObject) ja.getJSONObject(0).get("now");
                Const.weatherInfo.setTemperature(js.getString("tmp"));
                Const.weatherInfo.setText(js.getString("cond_txt"));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
