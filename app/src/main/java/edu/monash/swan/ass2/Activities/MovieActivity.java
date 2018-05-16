package edu.monash.swan.ass2.Activities;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import edu.monash.swan.ass2.Common.Const;
import edu.monash.swan.ass2.Common.NetworkUtil;
import edu.monash.swan.ass2.Movie.MovieInfo;
import edu.monash.swan.ass2.R;

public class MovieActivity extends AppCompatActivity {

    private ImageView mv_image;
    private TextView mv_year;
    private TextView mv_countries;
    private TextView mv_genres;
    private TextView mv_summary;
    private TextView mv_directors;
    private TextView mv_casts;
    private TextView mv_name;
    private MovieInfo movieInfo;
    private MyTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        initialize();
        if(Const.movieInfo != null && Const.movieInfo.getName().equals(Const.student.getFavouriteMovie())){
            movieInfo = Const.movieInfo;
            showMovieInfo();
        }else{
            mTask = new MyTask();
            mTask.execute("");
        }
    }

    private void initialize(){
        mv_image = findViewById(R.id.mv_image);
        mv_name = findViewById(R.id.mv_name);
        mv_year = findViewById(R.id.mv_year);
        mv_countries = findViewById(R.id.mv_countries);
        mv_genres = findViewById(R.id.mv_genres);
        mv_summary = findViewById(R.id.mv_summary);
        mv_directors = findViewById(R.id.mv_directors);
        mv_casts = findViewById(R.id.mv_casts);
    }

    private void showMovieInfo(){
        mv_name.setText(movieInfo.getName());
        mv_year.setText(movieInfo.getYear());
        mv_countries.setText(movieInfo.getCountries());
        mv_casts.setText(movieInfo.getCasts());
        mv_genres.setText(movieInfo.getGenres());
        mv_summary.setText(movieInfo.getSummary());
        mv_directors.setText(movieInfo.getDirectors());
        //得到可用的图片
        Bitmap bitmap = NetworkUtil.getHttpBitmap(movieInfo.getImage());
        //显示
        mv_image.setImageBitmap(bitmap);
    }

    private class MyTask extends AsyncTask<String, Integer, String> {
        //onPreExecute方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {

        }

        //doInBackground方法内部执行后台任务,不可在此方法内修改UI
        @Override
        protected String doInBackground(String... params) {
            movieInfo = MovieInfo.getMoive();
            return null;
        }

        //onProgressUpdate方法用于更新进度信息
        @Override
        protected void onProgressUpdate(Integer... progresses) {

        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(String result) {
            showMovieInfo();
        }

        //onCancelled方法用于在取消执行中的任务时更改UI
        @Override
        protected void onCancelled() {

        }
    }

}
