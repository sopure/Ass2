package edu.monash.swan.ass2.Fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import edu.monash.swan.ass2.Activities.MainActivity;
import edu.monash.swan.ass2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Map extends Fragment {

    public LocationClient mLocationClient;
    private TextView positionText;
    private MapView mapView;
    private BaiduMap baiduMap;          //定位自身
    private boolean isFirstLocate =true;

    public Map() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_map, container, false);
//        Context context = arg0.getContext();
//        context = context.getApplicationContext();
        mLocationClient = new LocationClient(getContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getContext());
//        setContentView(R.layout.activity_main);
        positionText = view.findViewById(R.id.position_text_view);
        mapView = view.findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);//定位标志
//        List<String> permissionList = new ArrayList<>();
        final double latitude=31.282820;//纬度
        final double longitude=120.747370;//经度
        //定义Maker坐标点
        LatLng point = new LatLng(latitude, longitude);
        LatLng point2= new LatLng(31.278487, 120.753448);
        LatLng point3= new LatLng(31.272820, 120.747370);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_geo);
        BitmapDescriptor bitmap2 = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);
        BitmapDescriptor bitmap3 = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_markb);
        //构建MarkerOption，用于在地图上添加Marker
        final OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap)
                .draggable(true);   //可以拖拽
        OverlayOptions option2 = new MarkerOptions()
                .position(point2)
                .icon(bitmap2)
                .draggable(true);
        OverlayOptions option3 = new MarkerOptions()
                .position(point3)
                .icon(bitmap3)
                .draggable(true);
        //在地图上添加Marker，并显示
        baiduMap.addOverlay(option);
        baiduMap.addOverlay(option2);
//        baiduMap.addOverlay(option3);
        //调用BaiduMap对象的setOnMarkerDragListener方法设置marker拖拽的监听
        baiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            public void onMarkerDrag(Marker marker) {
                //拖拽中
            }
            public void onMarkerDragEnd(Marker marker) {
                //拖拽结束
            }
            public void onMarkerDragStart(Marker marker) {
                //开始拖拽
            }
        });
        //对 marker 添加点击相应事件
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                // TODO Auto-generated method stub
//                Toast.makeText(getApplicationContext(), "Marker被点击了！", Toast.LENGTH_SHORT).show();
                //创建InfoWindow展示的view
                Button button = new Button(getContext());
                button.setBackgroundResource(R.drawable.popup_middle);
                button.setText("surname:Pan\n" +
                        "firstname:Yongchao\n" +
                        "sid:29184606\n" +
                        "address:Suzhou,jiangsu\n" +
//                        "course:MDCS\n" +
//                        "currentjob:unemployed\n" +
                        "dob:1995-03-23\n" +
                        "favouritemovie:Twilight\n" +
                        "favouritesport:basketball\n" +
                        "favouriteunit:FIT5187\n" +
                        "gender:male\n" +
                        "language:Chinese\n" +
                        "nationality:China\n" +
//                        "studymode:fulltime\n" +
                        "suburb:LinQuan street");
                button.setTextColor(Color.BLUE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        baiduMap.hideInfoWindow();
                    }
                });
                //定义用于显示该InfoWindow的坐标点
                LatLng pt = new LatLng(latitude, longitude);
                //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
                InfoWindow mInfoWindow = new InfoWindow(button, pt, -47);
                //显示InfoWindow
                baiduMap.showInfoWindow(mInfoWindow);
                return false;
            }
        });

//        //权限设置手机位置，手机储存，手机状态
//        if (ContextCompat.checkSelfPermission(Map.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
//            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
//        }
//        if (ContextCompat.checkSelfPermission(Map.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED) {
//            permissionList.add(Manifest.permission.READ_PHONE_STATE);
//        }
//        if (ContextCompat.checkSelfPermission(Map.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
//            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }
//        if (!permissionList.isEmpty()){
//            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
//            ActivityCompat.requestPermissions(Map.this, permissions,1);
//        }else {
//            requestLocation();
//        }

        requestLocation();
        return view;
                
    }
    //请求位置
    private  void requestLocation(){
        initLocation();
        mLocationClient.start();
    }
    private void initLocation(){           //增加一个initLocation方法，用setScanSpan函数设置5秒的屏幕刷新时间,达到实时更新位置的效果
        LocationClientOption option =new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }
    //定义三种方法
    @Override
    public void onResume(){
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onPause(){
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }
//    @Override
//    public void onRequestPermissionsResult(int requesCode,String[] permission,int[]grantResults){
//        switch (requesCode){
//            case 1:
//                if (grantResults.length>0){
//                    for (int result:grantResults){
//                        if (result !=PackageManager.PERMISSION_GRANTED){
//                            Toast.makeText(this,"必须同意所有权限才能使用本程序",Toast.LENGTH_SHORT).show();
//                            finish();
//                            return;
//                        }
//
//                    }
//                    requestLocation();
//                }else{
//                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//                break;
//            default:
//        }
//    }
    //加入navigateTo（）方法，把对象的地理位置信息经纬度封装在latlng对象中，并传入update中，来做到自身定位
    private void navigateTo(BDLocation location){
        if(isFirstLocate){    //isFirstLocate这个是变量是为了防止多次调用animateMapStaus方法。第一次定位调用即可
            LatLng latLng =new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update= MapStatusUpdateFactory.newLatLng(latLng);
            baiduMap.animateMapStatus(update);
            update=MapStatusUpdateFactory.zoomTo(16f);    //这个方法是讲地图缩放到16
            baiduMap.animateMapStatus(update);
            isFirstLocate=false;
        }
        MyLocationData.Builder locationBuilder =new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            StringBuilder currentPosition =new StringBuilder();
            navigateTo(location);
            currentPosition.append("定位地址: ").append(location.getProvince())
                    .append(location.getCity())
                    .append(location.getDistrict())
                    .append(location.getStreet());
            positionText.setText(currentPosition);
        }

    }

}
