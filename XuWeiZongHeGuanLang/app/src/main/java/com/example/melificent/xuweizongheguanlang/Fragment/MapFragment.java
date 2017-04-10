package com.example.melificent.xuweizongheguanlang.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.example.melificent.xuweizongheguanlang.Activity.ChartActivity;
import com.example.melificent.xuweizongheguanlang.Activity.LoginActivity;
import com.example.melificent.xuweizongheguanlang.Activity.VideoActivity;
import com.example.melificent.xuweizongheguanlang.Bean.MarkBean;
import com.example.melificent.xuweizongheguanlang.R;
import com.example.melificent.xuweizongheguanlang.Utils.MyConstants;
import com.example.melificent.xuweizongheguanlang.Utils.ShowImageTools;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/1/12.
 *实时监控各个子系统的状态
 */

public class MapFragment extends Fragment {
    @InjectView(R.id.message)
    Button message;
    @InjectView(R.id.map_form)
    Button map_form;
    @InjectView(R.id.my_location)
    Button my_location;
    @InjectView(R.id.spinner)
    Button spinner;
    @InjectView(R.id.mapview)
    MapView mapView;
    static Button map2d;
    static Button map3d;
    static Button map_form_dismiss;
    static Button monitor_disappear;

    static CheckBox Aarea,Barea,Carea,Darea,environment,wind,flight,water_pump,electric,video,invade,enter,electric_prow,electric_line,
    telecom_line,water_line,heat_line,gas_line,sewage_line,ch4,tempreture;

    BaiduMap baiduMap;
    double mLongtitude;
    double mLatitude;
    private boolean isFirstIn = true;
    private LocationClient locationClient;
    private PopupWindow popupWindow;
    private PopupWindow p;


    BitmapDescriptor environment_descriptor;
    ImageLoader imageloader;
    String wind_image_url;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getActivity().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.map_fragment,null);
        ButterKnife.inject(this,view);
        baiduMap = mapView.getMap();
        //初始化地图
        initLocation();
        //按钮设置监听
        setButtonListener();
        //set Marker Listener
        setMarkListener();
        initMarkeBitmapDescription();
        setMapClickListener();

        return view;

    }

    private void setMapClickListener() {
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                baiduMap.hideInfoWindow();
                Log.i("MapPoint", "onMapClick: "+latLng);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }

    private void initMarkeBitmapDescription() {
        environment_descriptor = BitmapDescriptorFactory.fromResource(R.drawable.tempreture1);
        baiduMap.clear();
    }


    private void setButtonListener() {
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChartActivity.class));
            }
        });

        map_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getActivity().getLayoutInflater().inflate(R.layout.map_form_select,null,false);
                popupWindow = new PopupWindow(view ,LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT,true);
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

        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baiduMap.clear();
                final View monitor = getActivity().getLayoutInflater().inflate(R.layout.monitor_pop_windows,null,false);
                p = new PopupWindow(monitor ,LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT,true);
                p.setAnimationStyle(R.style.AnimationFade);
                p.showAtLocation(v, Gravity.RIGHT,0,0);

                monitor_disappear = (Button) monitor.findViewById(R.id.monitor_pop_disappear);

                Aarea = (CheckBox) monitor.findViewById(R.id.Aarea);
                Barea = (CheckBox) monitor.findViewById(R.id.Barea);
                Carea = (CheckBox) monitor.findViewById(R.id.Carea);
                Darea = (CheckBox) monitor.findViewById(R.id.Darea);

                environment = (CheckBox) monitor.findViewById(R.id.monitor_environment);
                wind = (CheckBox) monitor.findViewById(R.id.monitor_wind);
                flight = (CheckBox) monitor.findViewById(R.id.monitor_flight);
                water_pump = (CheckBox) monitor.findViewById(R.id.monitor_water_pump);
                electric = (CheckBox) monitor.findViewById(R.id.monitor_electric);

                video = (CheckBox) monitor.findViewById(R.id.monitor_video);
                invade = (CheckBox) monitor.findViewById(R.id.monitor_invade);
                enter = (CheckBox) monitor.findViewById(R.id.monitor_enter);
                electric_prow = (CheckBox) monitor.findViewById(R.id.monitor_electric_prow);

                electric_line = (CheckBox) monitor.findViewById(R.id.monitor_electric_line);
                telecom_line = (CheckBox) monitor.findViewById(R.id.monitor_telecom_line);
                water_line = (CheckBox) monitor.findViewById(R.id.monitor_water_line);
                heat_line = (CheckBox) monitor.findViewById(R.id.monitor_heat_line);
                gas_line = (CheckBox) monitor.findViewById(R.id.monitor_gas_line);
                sewage_line = (CheckBox) monitor.findViewById(R.id.monitor_sewage_line);

                ch4 = (CheckBox) monitor.findViewById(R.id.monitor_ch4);
                tempreture = (CheckBox) monitor.findViewById(R.id.monitor_tempreture);

                //disappear pop windows
                monitor_disappear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (p.isShowing()&p!= null){
                            p.dismiss();
                        }
                        System.gc();
                    }
                });

                //Different Area Clicked ,show different area on BaiduMap
                Aarea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            MyConstants.Aarea_Clicked = true;
                            MyConstants.None_Area_Clicked = false;
                        }
                    }
                });

                //show the environment and equipment control system on BaiduMap
                environment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){

                            List<MarkBean> beans = new ArrayList<>();
                            beans.add(new MarkBean(25.18031247307013,102.96513396407245,BitmapDescriptorFactory.fromResource(R.drawable.environment)));
                            beans.add(new MarkBean(25.18031247307014,102.97705447818592,BitmapDescriptorFactory.fromResource(R.drawable.environment)));
                            setMark(beans);
                            setGroundOverlay1();
                        }
                    }
                });
                wind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){

                                List<MarkBean> winds = new ArrayList<MarkBean>();
                                winds.add(new MarkBean(25.196522004353394,102.9528900599755,BitmapDescriptorFactory.fromResource(R.drawable.wind)));
                                winds.add(new MarkBean(25.192124475180226,102.95522565430066,BitmapDescriptorFactory.fromResource(R.drawable.wind)));
                                setGroundOverlay1();
                                setMark(winds);

                            }

                        }
                });
                flight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){

                                List<MarkBean> flights = new ArrayList<MarkBean>();
                                flights.add(new MarkBean(25.180525017593137,102.95606107842467,BitmapDescriptorFactory.fromResource(R.drawable.flight)));
                                flights.add(new MarkBean(25.180263424280827,102.97520396883597,BitmapDescriptorFactory.fromResource(R.drawable.flight)));
                                setGroundOverlay1();
                                setMark(flights);
                                MyConstants.Monitor_PopWindows_First_Show = false;
                            }
                    }
                });
                water_pump.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){

                                List<MarkBean> water_pumps = new ArrayList<MarkBean>();
                                water_pumps.add(new MarkBean(25.199275608688875,102.94879378685137,BitmapDescriptorFactory.fromResource(R.drawable.water_pump)));
                                water_pumps.add(new MarkBean(25.1803369974573,102.9711975262628,BitmapDescriptorFactory.fromResource(R.drawable.water_pump)));
                                setGroundOverlay1 ();
                                setMark(water_pumps);
                            }
                    }
                });
                electric.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                                List<MarkBean> electrics = new ArrayList<MarkBean>();
                                electrics.add(new MarkBean(25.17595522775434,102.98872346675668,BitmapDescriptorFactory.fromResource(R.drawable.electric)));
                                electrics.add(new MarkBean(25.17822788192281,102.98435770197963,BitmapDescriptorFactory.fromResource(R.drawable.electric)));
                                setGroundOverlay1();
                                setMark(electrics);
                            }
                    }
                });

                //show safe system on BaiduMap
                video.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){

                                List<MarkBean> videoes = new ArrayList<MarkBean>();
                                videoes.add(new MarkBean(25.1956065468284,102.95492023042738,BitmapDescriptorFactory.fromResource(R.drawable.video)));
                                videoes.add(new MarkBean(25.174320198256183,102.99370007928032,BitmapDescriptorFactory.fromResource(R.drawable.video)));
                                setGroundOverlay1();
                            setMark(videoes);



                            }

                    }
                });

                invade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                                List<MarkBean> invades = new ArrayList<MarkBean>();
                                invades.add(new MarkBean(25.197355718545378,102.95165039837214,BitmapDescriptorFactory.fromResource(R.drawable.invade)));
                                invades.add(new MarkBean(25.18058224105452,102.95632158702247,BitmapDescriptorFactory.fromResource(R.drawable.invade)));
                                setGroundOverlay1();
                                setMark(invades);
                            }
                    }
                });
                enter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                                List<MarkBean> enters  = new ArrayList<MarkBean>();
                                enters.add(new MarkBean(25.180238899878685,102.97245515397636,BitmapDescriptorFactory.fromResource(R.drawable.enter)));
                                enters.add(new MarkBean(25.17524399264761,102.99059194221681,BitmapDescriptorFactory.fromResource(R.drawable.enter)));
                                setGroundOverlay1();
                                setMark(enters);
                            }
                    }
                });
                electric_prow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                                List<MarkBean> eletric_prows = new ArrayList<MarkBean>();
                                eletric_prows.add(new MarkBean(25.195369507585195,102.95531548485164,BitmapDescriptorFactory.fromResource(R.drawable.electric_prow)));
                                eletric_prows.add(new MarkBean(25.18059859060993,102.95677972283241,BitmapDescriptorFactory.fromResource(R.drawable.electric_prow)));
                                setGroundOverlay1();
                                setMark(eletric_prows);
                            }

                    }
                });

                //show line monitor system on BaiduMap
                electric_line.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                                List<MarkBean> electric_lines = new ArrayList<MarkBean>();
                                electric_lines.add(new MarkBean(25.180479568890326,102.966111853844417,BitmapDescriptorFactory.fromResource(R.drawable.electric_line)));
                                electric_lines.add(new MarkBean(25.175382969952338,102.990331433619,BitmapDescriptorFactory.fromResource(R.drawable.electric_line)));
                                setGroundOverlay1();
                                setMark(electric_lines);
                            }
                    }
                });
                telecom_line.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                                List<MarkBean> telecom_lines = new ArrayList<MarkBean>();
                                telecom_lines.add(new MarkBean(25.18032147307013,102.97529379938695,BitmapDescriptorFactory.fromResource(R.drawable.telecom_line)));
                                setGroundOverlay1();
                                setMark(telecom_lines);
                            }
                    }
                });
                water_line.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                                List<MarkBean> water_lines = new ArrayList<MarkBean>();
                                water_lines.add(new MarkBean(25.18059859060992,102.95642938368364,BitmapDescriptorFactory.fromResource(R.drawable.water_line)));
                                water_lines.add(new MarkBean(25.180435094956064,102.96490938769503,BitmapDescriptorFactory.fromResource(R.drawable.water_line)));
                                setGroundOverlay1();
                                setMark(water_lines);
                            }
                    }
                });
                heat_line.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                                List<MarkBean> heat_lines = new ArrayList<MarkBean>();
                                heat_lines.add(new MarkBean(25.18057406627599,102.95664497700596,BitmapDescriptorFactory.fromResource(R.drawable.heat_line)));
                                heat_lines.add(new MarkBean(25.180492318460143,102.96155870814393,BitmapDescriptorFactory.fromResource(R.drawable.heat_line)));
                                setGroundOverlay1();
                                setMark(heat_lines);
                            }
                    }
                });
                gas_line.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                                List<MarkBean> gas_lines = new ArrayList<MarkBean>();
                                gas_lines.add(new MarkBean(25.180287948677968,102.97463803636488,BitmapDescriptorFactory.fromResource(R.drawable.gas_line)));
                                gas_lines.add(new MarkBean(25.171270809002763,103.0043539862513,BitmapDescriptorFactory.fromResource(R.drawable.gas_line)));
                                setGroundOverlay1();
                                setMark(gas_lines);
                            }
                    }
                });
                sewage_line.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                                List<MarkBean> sewage_lines = new ArrayList<MarkBean>();
                                sewage_lines.add(new MarkBean(25.196652783431816,102.95280022942453,BitmapDescriptorFactory.fromResource(R.drawable.sewage_line)));
                                sewage_lines.add(new MarkBean(25.180557716717257,102.95647429895912,BitmapDescriptorFactory.fromResource(R.drawable.sewage_line)));
                                setGroundOverlay1();
                                setMark(sewage_lines);
                            }
                    }
                });

                //show gas monitor on BiaduMap, main about ch4
                ch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                                List<MarkBean> ch4s = new ArrayList<MarkBean>();
                                ch4s.add(new MarkBean(25.176363981664075,102.98715143211476,BitmapDescriptorFactory.fromResource(R.drawable.ch4)));
                                ch4s.add(new MarkBean(25.180770260807794,102.97967753027419,BitmapDescriptorFactory.fromResource(R.drawable.ch4)));
                                setGroundOverlay1();
                                setMark(ch4s);
                            }
                    }
                });

                // show fire accident on baidumap espercially monitor tempreture
                tempreture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                                List<MarkBean> tempretures = new ArrayList<MarkBean>();
                                tempretures.add(new MarkBean(25.180533192374998,102.9577049775074,BitmapDescriptorFactory.fromResource(R.drawable.tempreture2)));
                                tempretures.add(new MarkBean(25.18036969663242,102.96601430347194,BitmapDescriptorFactory.fromResource(R.drawable.tempreture2)));
                                setGroundOverlay1();
                                setMark(tempretures);
                            }
                    }
                });
//                monitor.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        if (p!=null&&p.isShowing()){
//                            p.dismiss();
//                            p = null;
//                        }
//                        return false;
//                    }
//                });
            }
        });



        //定位
        my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng= new LatLng(mLatitude,mLongtitude);
                MapStatusUpdate mapStatusUpdate= MapStatusUpdateFactory.newLatLng(latLng);
                baiduMap.animateMapStatus(mapStatusUpdate);
            }
        });

    }

//set Marker Listener of BaiduMap, click Marker to show infowindows
        private void setMarkListener() {
            baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(final Marker marker) {
                String lat = marker.getPosition().latitude+"";
                    Log.i("lat",lat);
                            switch (lat){
                                case "25.18031247307013":
                                View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.monitor_environment_info,null);
                                showInfoWindow(view,marker);
                                    break;
                                case "25.18031247307014":
                                    View view1 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.monitor_environment_info,null);
                                    showInfoWindow(view1,marker);
                                    break;
                                case "25.196522004353394":
                                    View view2 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.wind_info,null);
                                    ImageView imageview  = (ImageView) view2.findViewById(R.id.wind_equipment_info);
//                                    CreateOptions();
//                                    imageloader = ImageLoader.getInstance();
//                                    DisplayImageOptions options = new DisplayImageOptions.Builder()
//                                            .showImageOnLoading(R.drawable.wind_equipment)
//                                            .showImageForEmptyUri(R.mipmap.ic_launcher)
//                                            .showImageOnFail(R.mipmap.ic_launcher)
//                                            .cacheInMemory(true)
//                                            .cacheOnDisk(true)
//                                            .considerExifParams(true)
//                                            .displayer(new RoundedBitmapDisplayer(20))
//                                            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
//                                            .build();
//                                    ImageLoadingListener listener = new AnimateFirstDisplayListener();
//
//                                    wind_image_url ="drawable://"+R.drawable.wind_equipment;
//
//                                    imageloader.displayImage(wind_image_url,imageview,options,listener);
                                    ShowImageTools.DisplayImageOnImageview(imageloader,imageview,R.drawable.wind_equipment,getActivity());

                                    showInfoWindow(view2,marker);

                                    break;
                                case "25.192124475180226":
                                    View view3 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.wind_info2,null);
                                    ImageView imageview1  = (ImageView) view3.findViewById(R.id.wind_equipment_info2);
                                    ShowImageTools.DisplayImageOnImageview(imageloader,imageview1,R.drawable.wind_equipment,getActivity());
                                    showInfoWindow(view3,marker);
                                    break;
                                case "25.180525017593137":
                                    View view4 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.flight_info,null);
                                    ImageView imageview2 = (ImageView) view4.findViewById(R.id.flight_equipment);
                                    ShowImageTools.DisplayImageOnImageview(imageloader,imageview2,R.drawable.flight_equipment,getActivity());
                                    showInfoWindow(view4,marker);
                                    break;
                                case "25.180263424280827":
                                    View view5 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.flight_info_warnning,null);
                                    ImageView imageview3 = (ImageView) view5.findViewById(R.id.flight_equipment_warnning);
                                    ShowImageTools.DisplayImageOnImageview(imageloader,imageview3,R.drawable.flight_equipment,getActivity());
                                    showInfoWindow(view5,marker);
                                    break;
                                case "25.199275608688875":
                                    View view6 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.water_pump_info,null);
                                    ImageView imageview4 = (ImageView) view6.findViewById(R.id.water_pump_equipment);
                                    ShowImageTools.DisplayImageOnImageview(imageloader,imageview4,R.drawable.water_pump_equipment,getActivity());
                                    showInfoWindow(view6,marker);
                                    break;
                                case "25.1803369974573":
                                    View view7 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.water_pump_info1,null);
                                    ImageView imageview5 = (ImageView) view7.findViewById(R.id.water_pump_equipment1);
                                    ShowImageTools.DisplayImageOnImageview(imageloader,imageview5,R.drawable.water_pump_equipment,getActivity());
                                    showInfoWindow(view7,marker);
                                    break;
                                case "25.17595522775434":
                                    View view8 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.electric_info,null);
                                    ImageView imageview6 = (ImageView) view8.findViewById(R.id.electric_equipment);
                                    ShowImageTools.DisplayImageOnImageview(imageloader,imageview6,R.drawable.dsd,getActivity());
                                    showInfoWindow(view8,marker);
                                    break;
                                case "25.17822788192281":
                                    View view9 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.electric_info1,null);
                                    ImageView imageview7 = (ImageView) view9.findViewById(R.id.electric_equipment1);
                                    ShowImageTools.DisplayImageOnImageview(imageloader,imageview7,R.drawable.electric_equipment,getActivity());
                                    showInfoWindow(view9,marker);
                                    break;
                                case "25.1956065468284":
                                    startActivity(new Intent(getActivity(), VideoActivity.class));
                                    break;
                                case "25.174320198256183":
                                    startActivity(new Intent(getActivity(), VideoActivity.class));
                                    break;
                                case "25.197355718545378":
                                    View view10 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.invade_info,null);
                                    showInfoWindow(view10,marker);
                                    break;
                                case "25.18058224105452":
                                    View view11 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.invade_info_warnning,null);
                                    showInfoWindow(view11,marker);
                                    break;
                                case "25.180238899878685":
                                    View view12 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.enter_info,null);
                                    showInfoWindow(view12,marker);
                                    break;
                                case "25.17524399264761":
                                    View view13 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.enter_info1,null);
                                    showInfoWindow(view13,marker);
                                    break;
                                case "25.195369507585195":
                                    View view14 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.electric_prow_info,null);
                                    ImageView imageView = (ImageView) view14.findViewById(R.id.robot);
                                    ShowImageTools.DisplayImageOnImageview(imageloader,imageView,R.drawable.robot,getActivity());
                                    showInfoWindow(view14,marker);
                                    break;
                                case "25.18059859060993":
                                    View view15 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.electric_prow_info1,null);
                                    ImageView imageView1 = (ImageView) view15.findViewById(R.id.robot);
                                    ShowImageTools.DisplayImageOnImageview(imageloader,imageView1,R.drawable.robot,getActivity());
                                    showInfoWindow(view15,marker);
                                    break;
                                case "25.180479568890326":
//                                    View view16 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.electric_line_info,null);
//                                    showInfoWindow(view16,marker);
//                                    Toast.makeText(getActivity(),"electric line marker clicked!",Toast.LENGTH_LONG).show();
                                    break;
                                case "25.175382969952338":
                                    View view17 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.electric_line_info,null);
                                    showInfoWindow(view17,marker);
//                                    Toast.makeText(getActivity(),"electric line marker clicked!",Toast.LENGTH_LONG).show();
                                    break;
                                case "25.18032147307013":
                                    View view18 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.telecom_line_info,null);
                                    showInfoWindow(view18,marker);
                                    break;
                                case "25.18059859060992":
                                    View view19 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.water_line_info,null);
                                    showInfoWindow(view19,marker);
                                    break;
                                case "25.180435094956064":
                                    View view20 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.water_line_info1,null);
                                    showInfoWindow(view20,marker);
                                    break;
                                case "25.18057406627599":
                                    View view21 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.heat_line_info,null);
                                    showInfoWindow(view21,marker);
                                    break;
                                case "25.180492318460143":
                                    View view22  = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.heat_line_info1,null);
                                    showInfoWindow(view22,marker);
                                    break;
                                case "25.180287948677968":
                                    View view23 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.gas_line_info,null);
                                    showInfoWindow(view23,marker);
                                    break;
                                case "25.171270809002763":
                                    View view24 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.gas_line_info1,null);
                                    showInfoWindow(view24,marker);
                                    break;
                                case "25.196652783431816":
                                    View view25 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.sewage_line_info,null);
                                    showInfoWindow(view25,marker);
                                    break;
                                case "25.180557716717257":
                                    View view26 =LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.sewage_line_info,null);
                                    showInfoWindow(view26,marker);
                                    break;
                                case "25.176363981664075":
                                    View view27 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.ch4_info,null);
                                    showInfoWindow(view27,marker);
                                    break;
                                case "25.180770260807794":
                                    View view28 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.ch4_info1,null);
                                    showInfoWindow(view28,marker);
                                    break;
                                case "25.180533192374998":
                                    View view29 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.tempreture_info,null);
                                    showInfoWindow(view29,marker);
                                    break;
                                case "25.18036969663242":
                                    View view30 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.tempreture_info1,null);
                                    showInfoWindow(view30,marker);
                                    break;












                            }

                    return true;
                }
            });

}

    /**
     * display Infowindow on baidumap
     * @param view view for load infowindow
     * @param marker for get information from marker,such as position, of course also can get another information you need ,
     *               you need to set marker's information while you adding marker,caution the construction of the entity about marker
     */
    private void showInfoWindow(View view ,Marker marker) {
        final  LatLng latlng = marker.getPosition();
        InfoWindow info = new InfoWindow(view,latlng,-47);
        baiduMap.showInfoWindow(info);
    }


    private void initLocation() {
        locationClient=new LocationClient(getActivity());
        myBDlocationListener  listener= new myBDlocationListener();
        locationClient.registerLocationListener(listener);
        LocationClientOption option  = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        locationClient.setLocOption(option);
    }
    //add GroundOverlay on BaiduMap
    private  void setGroundOverlay(){
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
        baiduMap.addOverlay(optionsgroung);
        MapStatusUpdate msuground = MapStatusUpdateFactory.newLatLngBounds(latLngBounds);
        baiduMap.setMapStatus(msuground);
    }

    private  void setGroundOverlay1(){
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
        BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.drawable.line6);
        OverlayOptions optionsgroung = new GroundOverlayOptions()
                .positionFromBounds(latLngBounds)
                .image(descriptor)
                .transparency(0.8f)
                ;
        baiduMap.addOverlay(optionsgroung);
        MapStatusUpdate msuground = MapStatusUpdateFactory.newLatLngBounds(latLngBounds);
        baiduMap.setMapStatus(msuground);
    }

    /**
     * set Marker on BaiduMap by for each,at last use MapStatusUpdate to load the last point and refresh the map
     * @param beans use for transmit the information
     */
    private void setMark(List<MarkBean> beans){
        OverlayOptions markOptions = null;
        LatLng markLatLng = null;

        for (MarkBean bean: beans
             ) {
            markLatLng = new LatLng(bean.latitude,bean.longitude);
            markOptions  = new MarkerOptions().icon(bean.bitmapDescriptor).position(markLatLng).zIndex(8);
            baiduMap.addOverlay(markOptions);
        }
        MapStatusUpdate MSU = MapStatusUpdateFactory.newLatLng(markLatLng);
        baiduMap.setMapStatus(MSU);
    }


    private class myBDlocationListener implements BDLocationListener{

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

// ImageLoader option
    private void CreateOptions() {
        File cacheDir = new File(Environment.getExternalStorageDirectory(),"imageLoader.cache");
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration
                .Builder(getActivity())
                .memoryCacheExtraOptions(720,1080)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY-2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2*1024*1024))
                .memoryCacheSize(2*1024*1024)
                .diskCacheSize(50*1024*1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .diskCacheFileCount(100)
                .diskCache(new UnlimitedDiscCache(cacheDir))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(getActivity(),5*1000,30*1000))
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(configuration);
    }
        //First Display setting
    private  static  class  AnimateFirstDisplayListener extends SimpleImageLoadingListener {
        static  final List<String> displayedImages = Collections.synchronizedList(
                new LinkedList<String>()
        );
        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            super.onLoadingComplete(imageUri, view, loadedImage);
            if (loadedImage==null){
                ImageView imageview  = (ImageView) view;
                boolean firstdisplay= !displayedImages.contains(imageUri);
                if (firstdisplay){
                    FadeInBitmapDisplayer.animate(imageview,500);
                    displayedImages.add(imageUri);
                }
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
   //OnDestroy to release source of imageloader
    @Override
    public void onDestroy() {
        super.onDestroy();
        baiduMap.setMyLocationEnabled(false);
        locationClient.stop();
        mapView.onDestroy();
        imageloader.clearMemoryCache();
        imageloader.clearDiskCache();
    }

    @Override
    public void onResume() {
        super.onResume();
        LatLng latlng = new LatLng(25.192301115436, 102.957196977816);
        MapStatusUpdate MSU = MapStatusUpdateFactory.newLatLng(latlng);
        baiduMap.animateMapStatus(MSU);
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

}
