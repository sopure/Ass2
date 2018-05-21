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
        String url = "http://v.juhe.cn/movie/index?key=b77734c4768a4f2c4d73d06f2fa3625d&title="+name;
        MovieInfo movieInfo = null;
         String response = NetworkUtil.SendGet(url);
        // String response ="{\"resultcode\":\"200\",\"reason\":\"成功的返回\",\"result\":[{\"movieid\":\"10054\",\"rating\":\"9.1\",\"genres\":\"战争\\/喜剧\\/爱情\",\"runtime\":\"142 min\",\"language\":\"英语\",\"title\":\"阿甘正传\",\"poster\":\"http:\\/\\/img31.mtime.cn\\/mg\\/2014\\/06\\/17\\/145458.79721395_270X405X4.jpg\",\"writers\":\"温斯顿·格鲁姆,艾瑞克·罗斯\",\"film_locations\":\"美国\",\"directors\":\"罗伯特·泽米吉斯\",\"rating_count\":\"51749\",\"actors\":\"汤姆·汉克斯 Tom Hanks,罗宾·怀特 Robin Wright,加里·西尼斯 Gary Sinise,莎莉·菲尔德 Sally Field\",\"plot_simple\":\"阿甘的智商只有75，但凭借跑步的天赋，他顺利大学毕业。在越南战场，他结识了“捕虾迷”布巴和丹中尉。回国后，阿甘机缘巧合累积了大量资产。不过，钱并不是阿甘所看重的东西。阿甘和珍妮青梅竹马，可珍妮有自己的梦想……\",\"year\":\"1994\",\"country\":\"美国\",\"type\":null,\"release_date\":\"19940623\",\"also_known_as\":\"\"}],\"error_code\":0}";
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
