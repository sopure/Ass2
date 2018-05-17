package edu.monash.swan.ass2.Fragments;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.qiujuer.genius.blur.StackBlur;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import edu.monash.swan.ass2.Common.Const;
import edu.monash.swan.ass2.Common.NetworkUtil;
import edu.monash.swan.ass2.Movie.MovieInfo;
import edu.monash.swan.ass2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private  View view;
    private ImageView mv_image;
    private TextView mv_duration;
    private TextView mv_year;
    private TextView mv_countries;
    private TextView mv_genres;
    private TextView mv_summary;
    private TextView mv_directors;
    private TextView mv_casts;
    private TextView mv_name;
    private MovieInfo movieInfo;
    private MyTask mTask;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        initialize();
        if(Const.movieInfo != null && Const.movieInfo.getName().equals(Const.student.getFavouriteMovie())){
            movieInfo = Const.movieInfo;
            showMovieInfo();
        }else{
            mTask = new MyTask();
            mTask.execute("");
        }
        return view;
    }

    private void initialize(){
        mv_image = view.findViewById(R.id.mv_image);
        mv_name = view.findViewById(R.id.mv_name);
        mv_year = view.findViewById(R.id.mv_year);
        mv_countries = view.findViewById(R.id.mv_countries);
        mv_duration = view.findViewById(R.id.mv_duration);
        mv_genres = view.findViewById(R.id.mv_genres);
        mv_summary = view.findViewById(R.id.mv_summary);
        mv_directors = view.findViewById(R.id.mv_directors);
        mv_casts = view.findViewById(R.id.mv_casts);
    }

    private void showMovieInfo(){
        mv_name.setText(movieInfo.getName());
        mv_year.setText(movieInfo.getReleaseDate()+"上映");
        mv_countries.setText(movieInfo.getCountries());
        mv_duration.setText(movieInfo.getRunTime());
        mv_casts.setText(movieInfo.getActors());
        mv_genres.setText(movieInfo.getGenres());
        mv_summary.setText(movieInfo.getSummary());
        mv_directors.setText(movieInfo.getDirectors());

        if(Const.movieBitmap == null){
            Const.movieBitmap = NetworkUtil.getHttpBitmap(Const.movieInfo.getPoster());
        }
        //显示
        view.findViewById(R.id.mv_cover_bg).setBackground(new BitmapDrawable(StackBlur.blur(Const.movieBitmap, 60, false)));
        mv_image.setImageBitmap(Const.movieBitmap);
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
            Const.movieInfo = movieInfo;
            Const.movieBitmap = NetworkUtil.getHttpBitmap(Const.movieInfo.getPoster());
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
