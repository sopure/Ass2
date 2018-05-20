package edu.monash.swan.ass2.Fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    private TextView titleCity;
    private TextView titleTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;

    private Handler mHandler;

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

        titleCity = view.findViewById(R.id.title_city);
        titleTime = view.findViewById(R.id.title_time);
        degreeText = view.findViewById(R.id.degree_text);
        weatherInfoText = view.findViewById(R.id.weather_info_text);
        forecastLayout = view.findViewById(R.id.forecast_layout);
        aqiText = view.findViewById(R.id.aqi_text);
        pm25Text = view.findViewById(R.id.pm25_text);
        comfortText = view.findViewById(R.id.comfort_text);
        carWashText = view.findViewById(R.id.car_wash_text);
        sportText = view.findViewById(R.id.sport_text);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        long time = System.currentTimeMillis();
                        Date date = new Date(time);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String data = format.format(date);
                        titleTime.setText(data); //更新时间
                        break;
                    default:
                        break;

                }
            }
        };
        new TimeThread().start();

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

        String degree = weather.now.tmp + "℃";
        String weatherInfo = weather.now.cond_txt;

        titleCity.setText(cityName);

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
        String sport = "出行建议：" + weather.suggestion.sport;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);

        //图片资源
        if(Const.bingPicUrl == null){
            String requestBingPic = "http://guolin.tech/api/bing_pic";
            Const.bingPicUrl = NetworkUtil.SendGet(requestBingPic);
        }

        if(Const.weatherBitmap == null){
            Const.weatherBitmap = NetworkUtil.getHttpBitmap(Const.bingPicUrl);
        }
        //显示
        bingPicImg.setImageBitmap(Const.weatherBitmap);
    }

    class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;  //消息(一个整型值)
                    mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }

}
