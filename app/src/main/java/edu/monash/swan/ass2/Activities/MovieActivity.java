package edu.monash.swan.ass2.Activities;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import edu.monash.swan.ass2.Common.MyImageView;
import edu.monash.swan.ass2.Movie.MovieInfo;
import edu.monash.swan.ass2.R;

public class MovieActivity extends AppCompatActivity {

    private MyImageView mv_image;
    private TextView mv_year;
    private TextView mv_countries;
    private TextView mv_genres;
    private TextView mv_summary;
    private TextView mv_directors;
    private TextView mv_casts;
    private TextView mv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_movie);
        initialize();
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
        MovieInfo movieInfo = MovieInfo.getMoive();
        mv_image.setImageURL(movieInfo.getImage());
        mv_name.setText(movieInfo.getName());
        mv_year.setText(movieInfo.getYear());
        mv_countries.setText(movieInfo.getCountries());
        mv_casts.setText(movieInfo.getCasts());
        mv_genres.setText(movieInfo.getGenres());
        mv_summary.setText(movieInfo.getSummary());
        mv_directors.setText(movieInfo.getDirectors());
    }

}
