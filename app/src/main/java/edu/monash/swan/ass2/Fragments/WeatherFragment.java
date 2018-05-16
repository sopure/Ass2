package edu.monash.swan.ass2.Fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import edu.monash.swan.ass2.Common.Const;
import edu.monash.swan.ass2.Common.NetworkUtil;
import edu.monash.swan.ass2.R;
import edu.monash.swan.ass2.WeatherInfo.Forecast;
import edu.monash.swan.ass2.WeatherInfo.Weather;
import edu.monash.swan.ass2.WeatherInfo.WeatherUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {
    private View view;

    private ImageView bingPicImg;
    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;

    public WeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_weather, container, false);
        //初始化各控件
        bingPicImg = view.findViewById(R.id.bing_pic_img);
        weatherLayout = view.findViewById(R.id.weather_layout);
        titleCity = view.findViewById(R.id.title_city);
        titleUpdateTime = view.findViewById(R.id.title_update_time);
        degreeText = view.findViewById(R.id.degree_text);
        weatherInfoText = view.findViewById(R.id.weather_info_text);
        forecastLayout = view.findViewById(R.id.forecast_layout);
        aqiText = view.findViewById(R.id.aqi_text);
        pm25Text = view.findViewById(R.id.pm25_text);
        comfortText = view.findViewById(R.id.comfort_text);
        carWashText = view.findViewById(R.id.car_wash_text);
        sportText = view.findViewById(R.id.sport_text);

        if(Const.weather == null){
            Weather weather = WeatherUtil.requestWeather();
            showWeatherInfo(weather);
        }else{
            showWeatherInfo(Const.weather);
        }
        return view;
    }

    private void showWeatherInfo(Weather weather){
        String cityName = weather.now.city;
        String updateTime = weather.now.update.split(" ")[1];
        String degree = weather.now.tmp + "℃";
        String weatherInfo = weather.now.cond_txt;

        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime + "更新");
        degreeText.setText(degree);

        weatherInfoText.setText(weatherInfo);

        forecastLayout.removeAllViews();
        for(Forecast forecast : weather.forecastList){
            View view = LayoutInflater.from(WeatherFragment.this.getActivity()).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = view.findViewById(R.id.date_text);
            TextView infoText = view.findViewById(R.id.info_text);
            TextView maxText = view.findViewById(R.id.max_text);
            TextView minText = view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.cond_txt_d);
            maxText.setText(forecast.max);
            minText.setText(forecast.min);
            forecastLayout.addView(view);
        }
        if (weather.aqi != null) {
            aqiText.setText(weather.aqi.aqi);
            pm25Text.setText(weather.aqi.pm25);
        }
        String comfort = "舒适度：" + weather.suggestion.comf;
        String carWash = "洗车指数：" + weather.suggestion.drsg;
        String sport = "运行建议：" + weather.suggestion.sport;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);

        //图片资源
        if(Const.bingPicUrl == null){
            String requestBingPic = "http://guolin.tech/api/bing_pic";
            Const.bingPicUrl = NetworkUtil.SendGet(requestBingPic);
        }

        if(Const.bitmap == null){
            Const.bitmap = NetworkUtil.getHttpBitmap(Const.bingPicUrl);
        }
        //得到可用的图片
        bingPicImg = view.findViewById(R.id.bing_pic_img);
        //显示
        bingPicImg.setImageBitmap(Const.bitmap);

        weatherLayout.setVisibility(View.VISIBLE);
    }

}
