package edu.monash.swan.ass2.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import edu.monash.swan.ass2.WeatherInfo.Const;

public class MovieInfo {
    private String name;
    private String genres;
    private String year;
    private String image;
    private String countries;
    private String casts;
    private String summary;
    private String directors;

    public MovieInfo(String name, String year, String genres, String image, String countries, String casts, String summary, String directors) {
        this.name = name;
        this.year = year;
        this.genres = genres;
        this.image = image;
        this.countries = countries;
        this.casts = casts;
        this.summary = summary;
        this.directors = directors;
    }

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

    public static MovieInfo getMoive(){
        MovieInfo movieInfo = null;
        String name = Const.student.getFavouriteMovie();
        String url = "http://api.douban.com/v2/movie/search?q=" + name;

        String response = SendGet(url);
        int total = 0;
        String id = "";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            total = jsonObject.getInt("total");
            if(total == 0) return movieInfo;
            JSONArray ja = new JSONArray(jsonObject.get("subjects").toString());
            JSONObject js = (JSONObject) ja.get(0);
            id = js.getString("id");

        } catch (Exception e) {
            jsonObject = null;
        }
        url = "http://api.douban.com/v2/movie/subject/" + id;
        response = SendGet(url);
        try {
            jsonObject = new JSONObject(response);
            String year = jsonObject.getString("year");
            String image = jsonObject.getJSONObject("images").getString("small");
            String countries = "";
            JSONArray ja = jsonObject.getJSONArray("countries");
            for(int i = 0; i < ja.length(); i++){
                countries += ja.getString(i)+" ";
            }
            String genres = "";
            ja = jsonObject.getJSONArray("genres");
            for(int i = 0; i < ja.length(); i++){
                genres += ja.getString(i)+" ";
            }
            String casts = "";
            ja = jsonObject.getJSONArray("casts");
            for(int i = 0; i < ja.length(); i++){
                JSONObject js = ja.getJSONObject(i);
                casts += js.getString("name") + " ";
            }
            String summary = jsonObject.getString("summary");
            String directors = "";
            ja = jsonObject.getJSONArray("directors");
            for(int i = 0; i < ja.length(); i++){
                JSONObject js = ja.getJSONObject(i);
                directors += js.getString("name") + " ";
            }
            movieInfo = new MovieInfo(name, year, genres, image, countries, casts, summary, directors);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieInfo;
    }






    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getCasts() {
        return casts;
    }

    public void setCasts(String casts) {
        this.casts = casts;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
