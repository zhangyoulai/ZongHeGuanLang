package com.example.melificent.demoforkunming.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.VideoView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.example.melificent.demoforkunming.R;

/**
 * Created by p on 2016/10/27.
 */
public class TestActivity extends AppCompatActivity {
    MapView view;
    BaiduMap map;
    LocationClient locationClient;
    boolean isFirstIn = true;
    private double mLongtitude;
    private double mLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.a);
        view = (MapView) findViewById(R.id.mapview);
        map = view.getMap();
        initLocation();
        Intent intent = getIntent();
       double lat = intent.getDoubleExtra("lat",0);
        double lng  = intent.getDoubleExtra("lng",0);
        Log.i("location1122",lat+","+lng);
        OverlayOptions options = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.xiaofang))
                .zIndex(10).position(new LatLng(lat,lng));
        map.addOverlay(options);
        MapStatusUpdate msu  = MapStatusUpdateFactory.newLatLng(new LatLng(lat,lng));
        map.setMapStatus(msu);






    }

    private void setGroundOverlay() {
        LatLng southwest = new LatLng(25.19353856805835,102.95237802583497);
        LatLng northeast = new LatLng(25.192843784614455,102.95825294386829);
        LatLng p1 = new LatLng(25.202505001820153,102.94448192040488);
        LatLng p2 = new LatLng(25.164648545327633,103.02093760233384);
        LatLng p3  = new LatLng(25.20382427158124,102.94329497037817);
        LatLngBounds latLngBounds = new LatLngBounds.Builder()
                .include(southwest)
                .include(northeast)
                .include(p1)
                .include(p2)
                .include(p3)
                .build();
        BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.drawable.line5);
        OverlayOptions optionsgroung = new GroundOverlayOptions()
                .positionFromBounds(latLngBounds)
                .image(descriptor)
                .transparency(0.8f)
                ;
        map.addOverlay(optionsgroung);
        MapStatusUpdate msuground = MapStatusUpdateFactory.newLatLngBounds(latLngBounds);
       map.setMapStatus(msuground);
    }

    private void initLocation() {
        locationClient=new LocationClient(TestActivity.this);
        myBDlocationListener  listener= new myBDlocationListener();
        locationClient.registerLocationListener(listener);
        LocationClientOption option  = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        locationClient.setLocOption(option);
    }
    private class  myBDlocationListener implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //得到经纬度
            mLongtitude = bdLocation.getLongitude();
            mLatitude      = bdLocation.getLatitude();


            MyLocationData data = new MyLocationData.Builder().direction(1000)
                    .latitude(bdLocation.getLatitude()).longitude(bdLocation.getLongitude()).build();
            map.setMyLocationData(data);

            if (map.getMapStatus().zoom>=17){

                MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true, BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
                map.setMyLocationConfigeration(config);
            }else{
                MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,null);
                map.setMyLocationConfigeration(config);
            }



            setGroundOverlay();
            if(isFirstIn){
                LatLng latlng = new LatLng(25.192307, 102.957196);
                MapStatusUpdate MSU = MapStatusUpdateFactory.newLatLng(latlng);
                map.animateMapStatus(MSU);
                isFirstIn = false;
            }





        }
    }
    @Override
    public void onStop() {
        super.onStop();
        map.setMyLocationEnabled(false);
        locationClient.stop();
    }

    @Override
    public void onStart() {
        super.onStart();
        map.setMyLocationEnabled(true);
        if (!locationClient.isStarted()){
            locationClient.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map.setMyLocationEnabled(false);
        locationClient.stop();
        view.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
       view.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        view.onPause();
    }


}
