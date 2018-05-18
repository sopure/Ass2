package edu.monash.swan.ass2.Movie;

import org.json.JSONObject;

import edu.monash.swan.ass2.Common.Const;
import edu.monash.swan.ass2.Common.NetworkUtil;

public class MovieInfo {
    private String name;
    private String genres;
    private String releaseDate;
    private String poster;
    private String countries;
    private String actors;
    private String summary;
    private String directors;
    private String runTime;

    public MovieInfo(String name, String releaseDate, String genres, String poster, String countries, String actors, String summary, String directors, String runTime) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.genres = genres;
        this.poster = poster;
        this.countries = countries;
        this.actors = actors;
        this.summary = summary;
        this.directors = directors;
        this.runTime = runTime;
    }

    public static MovieInfo getMoive(String name){
        String url = "http://v.juhe.cn/movie/index?key=14eaa1ae73563d091377956d4a08270b&title="+name;
        MovieInfo movieInfo = null;
        String response = NetworkUtil.SendGet(url);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(response);
            jsonObject = (JSONObject) jsonObject.getJSONArray("result").get(0);
            String genres = jsonObject.getString("genres");
            String runTime = jsonObject.getString("runtime");
            String poster = jsonObject.getString("poster");
            String directors = jsonObject.getString("directors");
            String actors = jsonObject.getString("actors");
            actors = actors.replaceAll("[a-zA-Z]","" ).replace(",", "");
            String summary = jsonObject.getString("plot_simple");
            String countries = jsonObject.getString("country");
            StringBuilder sb = new StringBuilder(jsonObject.getString("release_date")).insert(4, '/').insert(7, '/');
            String releaseDate = sb.toString();

            movieInfo = new MovieInfo(name, releaseDate, genres, poster, countries, actors, summary, directors, runTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Const.movieInfo = movieInfo;
        return movieInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
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

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }
}
