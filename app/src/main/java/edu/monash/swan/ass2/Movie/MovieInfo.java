package edu.monash.swan.ass2.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.monash.swan.ass2.Common.Const;
import edu.monash.swan.ass2.Common.NetworkUtil;

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

    public static MovieInfo getMoive(){
        MovieInfo movieInfo = null;
        String name = Const.student.getFavouriteMovie();
        String url = "http://api.douban.com/v2/movie/search?q=" + name;

        String response = NetworkUtil.SendGet(url);
        int total = 0;
        String id = "";
        JSONObject jsonObject;
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
        response = NetworkUtil.SendGet(url);
        try {
            jsonObject = new JSONObject(response);
            String year = jsonObject.getString("year");
            String image = jsonObject.getJSONObject("images").getString("medium");
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
        Const.movieInfo = movieInfo;
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
