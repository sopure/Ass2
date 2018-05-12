package edu.monash.swan.ass2.WeatherInfo;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.monash.swan.ass2.R;

public class LocationUtils {
    public LocationClient mLocationClient;    //LocationClient类是定位SDK的核心类
    private TextView positionText;
    private Context context;
    public LocationUtils(Context context, TextView positionText){
        this.context = context;
        mLocationClient = new LocationClient(context.getApplicationContext());
        this.positionText = positionText;
    }

    public void doLocation(){
        mLocationClient.registerLocationListener(new MyLocationListener());
        initLocation();
        mLocationClient.start();
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span=1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //可以将定位的数据保存下来，这里新建的一个Const类，保存全局静态变量
            Const.CITY = location.getCity();
            WeatherInfo.getWeather();
            if(Const.weatherInfo.getText() != null){
                if(Const.weatherInfo.getText().contains("晴")){
                    ((Activity)context).findViewById(R.id.content_main).setBackgroundResource(R.drawable.qing);
                }else if(Const.weatherInfo.getText().contains("阴")){
                    ((Activity)context).findViewById(R.id.content_main).setBackgroundResource(R.drawable.yin);
                }else if(Const.weatherInfo.getText().contains("雨")){
                    ((Activity)context).findViewById(R.id.content_main).setBackgroundResource(R.drawable.yu);
                }else if(Const.weatherInfo.getText().contains("风")){
                    ((Activity)context).findViewById(R.id.content_main).setBackgroundResource(R.drawable.feng);
                }else if(Const.weatherInfo.getText().contains("雪")){
                    ((Activity)context).findViewById(R.id.content_main).setBackgroundResource(R.drawable.xue);
                }else if(Const.weatherInfo.getText().contains("云")){
                    ((Activity)context).findViewById(R.id.content_main).setBackgroundResource(R.drawable.duoyun);
                }
            }

            String text = Const.CITY + "\n" + Const.weatherInfo.getTemperature() + "℃  " + Const.weatherInfo.getText();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
            //获取当前时间
            Date date = new Date(System.currentTimeMillis());
            ((TextView)((Activity)context).findViewById(R.id.position_text_view2)).setText(text + "\n" + simpleDateFormat.format(date));
            positionText.setText(text);
            ((TextView)((Activity)context).findViewById(R.id.user)).setText("Welcome" + Const.student.getFirstName());
        }
    }

}
