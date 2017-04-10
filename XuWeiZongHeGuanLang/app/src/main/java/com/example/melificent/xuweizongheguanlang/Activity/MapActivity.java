package com.example.melificent.xuweizongheguanlang.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.platform.comapi.map.A;
import com.example.melificent.xuweizongheguanlang.Bean.MarkBean;
import com.example.melificent.xuweizongheguanlang.Fragment.MapFragment;
import com.example.melificent.xuweizongheguanlang.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/2/21.
 *
 */

public class MapActivity extends AppCompatActivity {
    @InjectView(R.id.inspection_routing)
    Button inspection_routing;
    @InjectView(R.id.map_form)
    Button map_form;
    @InjectView(R.id.my_location)
    Button my_location;
//    @InjectView(R.id.mapview)
    MapView mapView;
    static Button map2d;
    static Button map3d;
    static Button map_form_dismiss;
    static Button monitor_disappear;


    BaiduMap baiduMap;
    double mLongtitude;
    double mLatitude;
    private boolean isFirstIn = true;
    private LocationClient locationClient;
    private PopupWindow popupWindow;
    private PopupWindow p;
    BitmapDescriptor description;
    OverlayOptions options;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map_activity);
        mapView = (MapView) findViewById(R.id.mapview);
        baiduMap = mapView.getMap();
        ButterKnife.inject(this);
        //初始化地图
        initLocation();
        //按钮设置监听
        setButtonListener();
        //init marker
//        initMarker();
        //set marker Listener
//        setMarkerListener();
//        setMapclickListener();


    }

    private void setMapclickListener() {
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                List<MarkBean> beans = new ArrayList<MarkBean>();
                beans.add(new MarkBean(25.18031247307013,102.96513396407245,description));
                beans.add(new MarkBean(25.18031247307014,102.97705447818592,description));
                OverlayOptions options = null;
                LatLng latlng  = null ;
                for (MarkBean bean:beans
                        ) {
                    latlng = new LatLng(bean.latitude,bean.longitude);
                    options = new MarkerOptions().icon(bean.bitmapDescriptor).zIndex(8).position(latlng);
                    baiduMap.addOverlay(options);
                }
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latlng);
                baiduMap.setMapStatus(msu);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }

    private void setMarkerListener() {
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.infowindows,null);
                final LatLng latlng = marker.getPosition();
                InfoWindow info = new InfoWindow(view,latlng,-47);
                baiduMap.showInfoWindow(info);
                Log.i("marker'sPosition",marker.getPosition().toString());
                Log.i("marker'sIcon",marker.getIcon().hashCode()+"");

                return true;
            }
        });
    }

    private void initMarker() {
        description = BitmapDescriptorFactory.fromResource(R.drawable.tempreture1);
        baiduMap.clear();
    }

    private void setButtonListener() {

        map_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.map_form_select,null,false);
                popupWindow = new PopupWindow(view , LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT,true);
                popupWindow.setAnimationStyle(R.style.AnimationFade);
                popupWindow.showAtLocation(v, Gravity.RIGHT,0,0);
                map2d = (Button) view.findViewById(R.id.map2D);
                map3d = (Button) view.findViewById(R.id.map3D);
                map_form_dismiss = (Button) view.findViewById(R.id.popupwindows_disappear);
                map_form_dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow!=null&&popupWindow.isShowing()){
                            popupWindow.dismiss();
                            popupWindow=null;
                        }
                    }
                });
                map2d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                        if (popupWindow!=null&&popupWindow.isShowing()){
                            popupWindow.dismiss();
                            popupWindow=null;
                        }
                    }
                });
                map3d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                        if (popupWindow!=null&&popupWindow.isShowing()){
                            popupWindow.dismiss();
                            popupWindow=null;
                        }
                    }
                });
                view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (popupWindow!=null&&popupWindow.isShowing()){
                            popupWindow.dismiss();
                            popupWindow=null;
                        }
                        return false;
                    }
                });

            }
        });

        my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng= new LatLng(mLatitude,mLongtitude);
                MapStatusUpdate mapStatusUpdate= MapStatusUpdateFactory.newLatLng(latLng);
                baiduMap.animateMapStatus(mapStatusUpdate);
            }
        });

        inspection_routing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRoutingLine();
            }
        });
    }

    private void showRoutingLine() {
        BitmapDescriptor custom1 = BitmapDescriptorFactory.fromResource(R.drawable.line2);
        BitmapDescriptor custom2 = BitmapDescriptorFactory.fromResource(R.drawable.line2);
        BitmapDescriptor custom3 = BitmapDescriptorFactory.fromResource(R.drawable.line2);

        LatLng point1 = new LatLng(25.168327625596923,103.01295974940788);
        LatLng point2 = new LatLng(25.17550559685287,102.98742990682274);
        LatLng point3 = new LatLng(25.179748410788036,102.96812532141969);
        LatLng point4 = new LatLng(25.179723886281106,102.95943870714108);
        LatLng point5 = new LatLng(25.186696820074346,102.95492921348247);
        List<BitmapDescriptor> customlist = new ArrayList<BitmapDescriptor>();
        customlist.add(custom1);
        customlist.add(custom2);
        customlist.add(custom3);


        List<LatLng> points = new ArrayList<LatLng>();
        List<Integer> indexs = new ArrayList<Integer>();
        points.add(point1);
        indexs.add(0);
        points.add(point2);
        indexs.add(0);
        points.add(point3);
        indexs.add(1);
        points.add(point4);
        indexs.add(2);
        points.add(point5);

        OverlayOptions overlayOptions = new PolylineOptions().width(15).color(0xAAFF0000)
                .points(points).customTextureList(customlist).textureIndex(indexs).zIndex(30);
        baiduMap.addOverlay(overlayOptions);
        MapStatusUpdate msu  = MapStatusUpdateFactory.newLatLng(new LatLng(25.173298293559576,102.99529008003245) );
         baiduMap.setMapStatus(msu);

    }

    private void initLocation() {
        locationClient=new LocationClient(MapActivity.this);
        myBDlocationListener listener= new myBDlocationListener();
        locationClient.registerLocationListener(listener);
        LocationClientOption option  = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        locationClient.setLocOption(option);
    }
    private class myBDlocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            mLongtitude = bdLocation.getLongitude();
            mLatitude = bdLocation.getLatitude();
            MyLocationData data = new MyLocationData.Builder().direction(1000)
                    .latitude(bdLocation.getLatitude()).longitude(bdLocation.getLongitude()).build();
            baiduMap.setMyLocationData(data);
            if(isFirstIn){
                LatLng latlng = new LatLng(25.192301115436, 102.957196977816);
                MapStatusUpdate MSU = MapStatusUpdateFactory.newLatLng(latlng);
                baiduMap.animateMapStatus(MSU);
                isFirstIn = false;
            }
        }
    }
    //实现地图生命周期的管理方法

    @Override
    public void onStop() {
        super.onStop();
        baiduMap.setMyLocationEnabled(false);
        locationClient.stop();
    }

    @Override
    public void onStart() {
        super.onStart();
        baiduMap.setMyLocationEnabled(true);
        if (!locationClient.isStarted()){
            locationClient.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        baiduMap.setMyLocationEnabled(false);
        locationClient.stop();
        mapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();


        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }
}
