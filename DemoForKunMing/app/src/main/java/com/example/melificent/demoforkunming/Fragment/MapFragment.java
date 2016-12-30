package com.example.melificent.demoforkunming.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

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
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.example.melificent.demoforkunming.Activity.BiaoShiActivity;
import com.example.melificent.demoforkunming.Activity.GongdianActivity;
import com.example.melificent.demoforkunming.Activity.VideoActivity;
import com.example.melificent.demoforkunming.Activity.VideoRuqinActivity;
import com.example.melificent.demoforkunming.Activity.WarnningActivity;
import com.example.melificent.demoforkunming.Activity.ZhaoMingActivity;
import com.example.melificent.demoforkunming.R;
import com.example.melificent.demoforkunming.Spinner.Infoentity;
import com.example.melificent.demoforkunming.Spinner.SpinnerDataAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p on 2016/10/24.
 * 实现思路：
 * 1.初始化百度地图，一般在setcontentView或者是fragment中引入布局之前初始化，百度地图是单例的，只需一次初始化
 * 2.初始化百度地图的相关属性（初始化地点在管廊所在地）
 * 3.点击按钮为百度地图设置卫星图
 * 4.点击我的位置来达到快速定位的功能
 * 5.通过spinner来显示监测监控的9大项
 * 6.点击每一项来响应具体的操作，在地图上放置marker，并且放置一个表示管廊的groundoverlay，跟随地图进行同步的缩放
 * 7.响应marker的点击效果，弹出info window，如果info window上有button，那么点击按钮完成相关响应
 */
public class MapFragment extends Fragment {
    BaiduMap baiduMap;
    MapView mapView;
    LocationClient locationClient;
    private ImageButton myLocation;
    Button zhaomingbutton;
    Button warnningbutton;
    Button biaoshi;
    Button ruqinjiankong;
    Button button_satallite;
    Button button_2D;

      double mLatitude;
    double mLongtitude;
    private boolean isFirstIn = true;
    BitmapDescriptor mMarker;
    //放置地图marker的bitmap descriptor
    BitmapDescriptor marker_video;
    BitmapDescriptor marker_light;
    BitmapDescriptor marker_warnning;
    BitmapDescriptor marker_ruqinbaojing;
    BitmapDescriptor marker_biaoshi;
    BitmapDescriptor marker_paishui;
    BitmapDescriptor marker_gongdian;
    BitmapDescriptor marker_tongfeng;
    BitmapDescriptor marker_xiaofang;
    BitmapDescriptor descriptor;
    Spinner spinner;
    SpinnerDataAdapter adapter;

    //初始化地图控件
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getActivity().getApplicationContext());
    }
    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.mapfragment,container,false);
        //初始化控件
        myLocation = (ImageButton) view.findViewById(R.id.orientate);
        mapView = (MapView) view.findViewById(R.id.bmapView);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        baiduMap = mapView.getMap();
        button_satallite  = (Button) view.findViewById(R.id.button_satellite);
        button_2D = (Button) view.findViewById(R.id.button_2D);

        setMapListener();
        //定位
        setButtonListener();
        //初始化百度地图
        initLocation();
        //放置marker
        setMarker();
        //spinner初始化
        initSpinner();
        //spinner设置监听器
        SetSpinnerListener();
        //初始化Spinner图标
        initMarkerView();

        return view;
    }
    //初始化Spinner图标
    private void initMarkerView() {
        marker_video= BitmapDescriptorFactory.fromResource(R.drawable.video);
        marker_biaoshi = BitmapDescriptorFactory.fromResource(R.drawable.a1);
        marker_gongdian = BitmapDescriptorFactory.fromResource(R.drawable.electric);
        marker_light = BitmapDescriptorFactory.fromResource(R.drawable.light);
        marker_ruqinbaojing = BitmapDescriptorFactory.fromResource(R.drawable.ruqinbaojing);
        marker_paishui = BitmapDescriptorFactory.fromResource(R.drawable.water);
        marker_tongfeng =BitmapDescriptorFactory.fromResource(R.drawable.tongfen);
        marker_warnning = BitmapDescriptorFactory.fromResource(R.drawable.warnning);
        marker_xiaofang = BitmapDescriptorFactory.fromResource(R.drawable.xiaofang);
        baiduMap.clear();
    }

    private void SetSpinnerListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SetMarker(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
        //在地图上放置marker，通过构建实体类来完成（for循环完成放置多个marker的目的）
    private void SetMarker(int position) {
        switch (position){
            case 0 :
                baiduMap.clear();
                setGroundOverlay();
                break;
            case 1:
                    List<Info> videoes = new ArrayList<>();
                    videoes.add(new Info(25.1956065468284,102.95492023042738,marker_video));
                    videoes.add(new Info(25.174320198256183,102.99370007928032,marker_video));
                    videoes.add(new Info(25.18031247307013,102.96513396407245,marker_video));
                    videoes.add(new Info(25.18031247307014,102.97705447818592,marker_video));

                baiduMap.clear();
                    OverlayOptions optionsa = null;
                    LatLng latLng = null;
                    Marker Mmarker = null;
                for (Info video:videoes
                     ) {
                    LatLng latlng = new LatLng(video.latitude,video.longitude);
                    optionsa = new MarkerOptions().icon(marker_video).position(latlng).zIndex(5);
                    Mmarker = (Marker) baiduMap.addOverlay(optionsa);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("info",video);
                    Mmarker.setExtraInfo(bundle);

                }
                    MapStatusUpdate msu  = MapStatusUpdateFactory.newLatLng(latLng);
                    baiduMap.setMapStatus(msu);
                    setGroundOverlay();
                    break;


            case 2:
                baiduMap.clear();
               List<Info> lightes = new ArrayList<>();
                lightes.add(new Info(25.196522004353394,102.9528900599755,R.drawable.video,"a","1",1));
                lightes.add(new Info(25.192124475180226,102.95522565430066,R.drawable.video,"a","1",1));
                lightes.add(new Info(25.180525017593137,102.95606107842467,R.drawable.video,"a","1",1));
                lightes.add(new Info(25.180263424280827,102.97520396883597,R.drawable.video,"a","1",1));
                OverlayOptions options_light= null;
                LatLng latlng_light = null;
                for (Info light: lightes
                     ) {
                    latlng_light = new LatLng(light.latitude,light.longitude);
                    options_light = new MarkerOptions().icon(marker_light).position(latlng_light).zIndex(8);
                    baiduMap.addOverlay(options_light);
                }

                MapStatusUpdate msu1  = MapStatusUpdateFactory.newLatLng(latlng_light);
                baiduMap.setMapStatus(msu1);
                setGroundOverlay();
                break;
            case 3:
                baiduMap.clear();
                List<Info> warnning = new ArrayList<>();
                warnning.add(new Info(25.199275608688875,102.94879378685137,marker_warnning));
                warnning.add(new Info(25.1803369974573,102.9711975262628,marker_warnning));
                warnning.add(new Info(25.17595522775434,102.98872346675668,marker_warnning));
                warnning.add(new Info(25.17822788192281,102.98435770197963,marker_warnning));
                OverlayOptions options_warnning;
                LatLng latlng_warnning = null;
                for (Info warn: warnning
                     ) {
                    latlng_warnning = new LatLng(warn.latitude,warn.longitude);
                    options_warnning = new MarkerOptions().icon(warn.bitmapDescriptor).zIndex(8).position(latlng_warnning);
                    baiduMap.addOverlay(options_warnning);
                }
                MapStatusUpdate msu2  = MapStatusUpdateFactory.newLatLng(latlng_warnning);
                baiduMap.setMapStatus(msu2);
                setGroundOverlay();
                break;
            case 4:
                baiduMap.clear();
            List<Info> ruqinbaojings = new ArrayList<>();
                ruqinbaojings.add(new Info(25.197355718545378,102.95165039837214,marker_ruqinbaojing));
                ruqinbaojings.add(new Info(25.18058224105452,102.95632158702247,marker_ruqinbaojing));
                ruqinbaojings.add(new Info(25.180238899878685,102.97245515397636,marker_ruqinbaojing));
                ruqinbaojings.add(new Info(25.17524399264761,102.99059194221681,marker_ruqinbaojing));

                OverlayOptions options_ruqinbaojing;
                LatLng latlng_ruqinbaojing=null;
                for (Info ruqinbaojing:ruqinbaojings
                     ) {
                    latlng_ruqinbaojing = new LatLng(ruqinbaojing.latitude,ruqinbaojing.longitude);
                    options_ruqinbaojing = new MarkerOptions().icon(ruqinbaojing.bitmapDescriptor).zIndex(9).position(latlng_ruqinbaojing);
                    baiduMap.addOverlay(options_ruqinbaojing);
                }
                MapStatusUpdate msu3  = MapStatusUpdateFactory.newLatLng(latlng_ruqinbaojing);
                baiduMap.setMapStatus(msu3);
                setGroundOverlay();
                break;
            case 5:
                baiduMap.clear();
                List<Info> biaoshis = new ArrayList<>();
                biaoshis.add(new Info(25.195369507585195,102.95531548485164,marker_biaoshi));
                biaoshis.add(new Info(25.18059859060993,102.95677972283241,marker_biaoshi));
                biaoshis.add(new Info(25.180479568890326,102.966111853844417,marker_biaoshi));
                biaoshis.add(new Info(25.175382969952338,102.990331433619,marker_biaoshi));

                OverlayOptions options_biaoshi;
                LatLng latlng_biaoshi=null;
                for (Info ruqinbaojing:biaoshis
                        ) {
                    latlng_biaoshi = new LatLng(ruqinbaojing.latitude,ruqinbaojing.longitude);
                    options_biaoshi = new MarkerOptions().icon(ruqinbaojing.bitmapDescriptor).zIndex(9).position(latlng_biaoshi);
                    baiduMap.addOverlay(options_biaoshi);
                }
                MapStatusUpdate msu4  = MapStatusUpdateFactory.newLatLng(latlng_biaoshi);
                baiduMap.setMapStatus(msu4);
                setGroundOverlay();
                break;
            case 6:
                baiduMap.clear();
              List<Info> paishuis = new ArrayList<>();
                paishuis.add(new Info(25.196571046524443,102.95303378885704,marker_paishui));
                paishuis.add(new Info(25.18032147307013,102.97529379938695,marker_paishui));
                paishuis.add(new Info(25.18059859060992,102.95642938368364,marker_paishui));
                paishuis.add(new Info(25.180435094956064,102.96490938769503,marker_paishui));
                OverlayOptions options_paishui;
                LatLng latlng_paishui = null;
                for (Info paishui: paishuis
                     ) {
                    latlng_paishui = new LatLng(paishui.latitude,paishui.longitude);
                    options_paishui  = new MarkerOptions().icon(paishui.bitmapDescriptor).position(latlng_paishui).zIndex(8);
                    baiduMap.addOverlay(options_paishui);
                }
                MapStatusUpdate msu5  = MapStatusUpdateFactory.newLatLng(latlng_paishui);
                baiduMap.setMapStatus(msu5);
                setGroundOverlay();
                break;
            case 7:
                baiduMap.clear();
                List<Info> gongdians = new ArrayList<>();
                gongdians.add(new Info(25.18057406627599,102.95664497700596,marker_gongdian));
                gongdians.add(new Info(25.180492318460143,102.96155870814393,marker_gongdian));
                gongdians.add(new Info(25.180287948677968,102.97463803636488,marker_gongdian));
                gongdians.add(new Info(25.171270809002763,103.0043539862513,marker_gongdian));
                OverlayOptions options_gongdian;
                LatLng latlng_gongdian = null;
                for (Info paishui: gongdians
                        ) {
                    latlng_gongdian = new LatLng(paishui.latitude,paishui.longitude);
                    options_gongdian  = new MarkerOptions().icon(paishui.bitmapDescriptor).position(latlng_gongdian).zIndex(8);
                    baiduMap.addOverlay(options_gongdian);
                }
                MapStatusUpdate msu6  = MapStatusUpdateFactory.newLatLng(latlng_gongdian);
                baiduMap.setMapStatus(msu6);
                setGroundOverlay();
                break;
            //未完待续
            case 8:
                baiduMap.clear();
                List<Info> tongfengs = new ArrayList<>();
                tongfengs.add(new Info(25.196652783431816,102.95280022942453,marker_tongfeng));
                tongfengs.add(new Info(25.180557716717257,102.95647429895912,marker_tongfeng));
                tongfengs.add(new Info(25.176363981664075,102.98715143211476,marker_tongfeng));
                tongfengs.add(new Info(25.180770260807794,102.97967753027419,marker_tongfeng));
                OverlayOptions options_tongfeng;
                LatLng latlng_tongfeng = null;
                for (Info tongfeng: tongfengs
                        ) {
                    latlng_tongfeng = new LatLng(tongfeng.latitude,tongfeng.longitude);
                    options_tongfeng  = new MarkerOptions().icon(tongfeng.bitmapDescriptor).position(latlng_tongfeng).zIndex(8);
                    baiduMap.addOverlay(options_tongfeng);
                }
                MapStatusUpdate msu7  = MapStatusUpdateFactory.newLatLng(latlng_tongfeng);
                baiduMap.setMapStatus(msu7);
                setGroundOverlay();
                break;
            case 9:
                baiduMap.clear();
                List<Info> xiaofangs = new ArrayList<>();
                xiaofangs.add(new Info(25.180533192374998,102.9577049775074,marker_xiaofang));
                xiaofangs.add(new Info(25.18036969663242,102.96601430347194,marker_xiaofang));
                xiaofangs.add(new Info(25.19945632577237,102.94841469853728,marker_xiaofang));
                xiaofangs.add(new Info(25.17416486930052,102.9943019439718,marker_xiaofang));
                OverlayOptions options_xiaofang;
                LatLng latlng_xiaofang = null;
                for (Info xiaofang: xiaofangs
                        ) {
                    latlng_xiaofang = new LatLng(xiaofang.latitude,xiaofang.longitude);
                    options_xiaofang  = new MarkerOptions().icon(xiaofang.bitmapDescriptor).position(latlng_xiaofang).zIndex(8);
                    baiduMap.addOverlay(options_xiaofang);
                }
                MapStatusUpdate msu8  = MapStatusUpdateFactory.newLatLng(latlng_xiaofang);
                baiduMap.setMapStatus(msu8);
                setGroundOverlay();
                break;








        }
    }
                    //初始化spinner用于显示marker item
    private void initSpinner() {
        List<Infoentity> infos = new ArrayList<>();
        infos.add(new Infoentity("设备"));
        infos.add(new Infoentity("探头",BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.video)));
        infos.add(new Infoentity("照明",BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.zhaomingbaojing)));
        infos.add(new Infoentity("监控",BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.warnning)));
        infos.add(new Infoentity("入侵",BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.ruqinbaojing)));
        infos.add(new Infoentity("标识",BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.a1)));
        infos.add(new Infoentity("排水",BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.water)));
        infos.add(new Infoentity("供电",BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.electric)));
        infos.add(new Infoentity("通风",BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.tongfen)));
        infos.add(new Infoentity("消防",BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.xiaofang)));

        adapter = new SpinnerDataAdapter(getActivity(),infos);
        spinner.setAdapter(adapter);
    }

    private void setMarker() {
        mMarker = BitmapDescriptorFactory.fromResource(R.drawable.video);
        baiduMap.clear();
    }
            //初始化百度地图的位置相关属性
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

                //为button设置监听器
    private void setButtonListener() {
        //设置普通地图
        button_2D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baiduMap.setMapType(baiduMap.MAP_TYPE_NORMAL);
            }
        });
        //设置卫星监听
        button_satallite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
            }
        });
        //设置我的位置监听
        myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latlng = new LatLng(mLatitude,mLongtitude);
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latlng);
                Log.i("latlng",latlng.latitude+","+latlng.longitude);
                baiduMap.animateMapStatus(msu);
                baiduMap.clear();

            }
        });
//        displsy.setOnClickListener(new View.OnClickListener() {
//            //点击地图上按钮出现摄像头图标
//            @Override
//            public void onClick(View v) {
//            addOverlay(Info.infos);
//
//            }
//
//
//        });
                   //实现不同的marker点击实现不同的点击效果，通过marker的position.latitue来实现不同的判断
//        //一定要是唯一标识符



        //地图上为marker设置监听器，通过判断每一个marker的latitude来判断，实际证明通过lng来判断
        //或者传值bundle（setextrainfo）来判断更好
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
               String lat=  marker.getPosition().latitude+"";
                switch (lat){
                    case "25.1956065468284":
                        //播放监控视频
                        playVideo();
                        break;
                    case "25.174320198256183":
                        //播放监控视频
                        playVideo();
                        break;
                    case "25.18031247307013":
                        //播放监控视频
                        playVideo();
                        break;
                    case "25.18031247307014":
                        //播放监控视频
                        playVideo();
                        break;
                    case "25.196522004353394":
                        //展示照明相关的infowindows
                        showLightsInfoWindows(marker);

                         break;
                    case "25.192124475180226":
                        showLightsInfoWindows(marker);
                        break;
                    case "25.180525017593137":
                        showLightsInfoWindows(marker);
                        break;
                    case "25.180263424280827":
                        showLightsInfoWindows(marker);
                        break;
                    case  "25.199275608688875":
                        //报警info windows
                        showWarnningInfoWindows(marker);
                        break;
                    case  "25.1803369974573":
                        showWarnningInfoWindows(marker);
                        break;
                    case  "25.17595522775434":
                        showWarnningInfoWindows(marker);
                        break;
                    case  "25.17822788192281":
                        showWarnningInfoWindows(marker);
                        break;


                    case "25.195369507585195":
                        //标识
                        showBiaoshiInfoWindows(marker);
                        break;
                    case "25.18059859060993":
                        showBiaoshiInfoWindows(marker);
                        break;
                    case "25.180479568890327":
                        showBiaoshiInfoWindows(marker);
                        break;
                    case "25.175382969952338":
                        showBiaoshiInfoWindows(marker);
                        break;
                    case "25.196571046524443":
                        //排水
                        showPaishuiIndoWindows(marker);
                        break;
                    case "25.18032147307013":
                        showPaishuiIndoWindows(marker);
                        break;
                    case "25.18059859060992":
                        showPaishuiIndoWindows(marker);
                        break;
                    case "25.180435094956064":
                        showPaishuiIndoWindows(marker);
                        ;
                        break;
                    case "25.18057406627599":
                        //供电
                        showGongdianInfoWindows(marker);
                        break;
                    case "25.180492318460143":
                        showGongdianInfoWindows(marker);
                        break;
                    case "25.180287948677968":
                        showGongdianInfoWindows(marker);
                        break;
                    case "25.171270809002763":
                        showGongdianInfoWindows(marker);
                        break;
                    case "25.196652783431816":
                        //通风
                        showTongfengInfoWindows(marker);
                        break;
                    case "25.180557716717257":
                        showTongfengInfoWindows(marker);
                        break;
                    case "25.176363981664075":
                        showTongfengInfoWindows(marker);
                        break;
                    case "25.180770260807794":
                        showTongfengInfoWindows(marker);
                        break;
                    case "25.180533192374998":
                        //消防
                        showXiaofangInfoWindows(marker);
                        break;
                    case "25.18036969663242":
                        showXiaofangInfoWindows(marker);
                        break;
                    case "25.19945632577237":
                        showXiaofangInfoWindows(marker);
                        break;
                    case "25.17416486930052":
                        showXiaofangInfoWindows(marker);
                        break;

                    case  "25.197355718545378":
                        //入侵报警
                        showRuqinbaojingInfoWindows(marker);
                        break;
                    case  "25.18058224105452":
                        showRuqinbaojingInfoWindows(marker);
                        break;
                    case  "25.180238899878685":
                        showRuqinbaojingInfoWindows(marker);
                        break;
                    case  "25.17524399264761":
                        showRuqinbaojingInfoWindows(marker);
                        break;
                }
                
                return true;
            }
        });

    }

    private void showXiaofangInfoWindows(Marker marker) {
        LayoutInflater inflater7 = LayoutInflater.from(getActivity());
        final  View view7 = inflater7.inflate(R.layout.xiaofang,null);
        final  LatLng latLng7 = marker.getPosition();
        InfoWindow window7= new InfoWindow(view7,latLng7,-47);
        baiduMap.showInfoWindow(window7);
    }

    private void showTongfengInfoWindows(Marker marker) {
        LayoutInflater inflater6 = LayoutInflater.from(getActivity());
        final  View view6 = inflater6.inflate(R.layout.tongfeng,null);
        final  LatLng latLng6 = marker.getPosition();
        InfoWindow window6 = new InfoWindow(view6,latLng6,-47);
        baiduMap.showInfoWindow(window6);
    }

    private void showGongdianInfoWindows(Marker marker) {
        LayoutInflater inflater5 = LayoutInflater.from(getActivity());
        final  View view5 = inflater5.inflate(R.layout.gongdian,null);
        ruqinjiankong = (Button) view5.findViewById(R.id.button_gongdian);
        final  LatLng latLng5 = marker.getPosition();
        InfoWindow window5 = new InfoWindow(view5,latLng5,-47);
        baiduMap.showInfoWindow(window5);
        ruqinjiankong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent();
                i.setClass(getActivity(), GongdianActivity.class);
                startActivity(i);
            }
        });
    }

    private void showPaishuiIndoWindows(Marker marker) {
        LayoutInflater inflater4 = LayoutInflater.from(getActivity());
        final  View view4 = inflater4.inflate(R.layout.paishui,null);
        final  LatLng latLng4 = marker.getPosition();
        InfoWindow window4 = new InfoWindow(view4,latLng4,-47);
        baiduMap.showInfoWindow(window4);
    }

    private void showBiaoshiInfoWindows(Marker marker) {
        LayoutInflater inflater3 = LayoutInflater.from(getActivity());
        final  View view3 = inflater3.inflate(R.layout.biaoshi,null);
        biaoshi = (Button) view3.findViewById(R.id.button_biaoshi);
        final  LatLng latLng3 = marker.getPosition();
        InfoWindow window3 = new InfoWindow(view3,latLng3,-47);
        baiduMap.showInfoWindow(window3);

        biaoshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent();
                I.setClass(getActivity(), BiaoShiActivity.class);
                startActivity(I);
            }
        });
    }

    private void showRuqinbaojingInfoWindows(Marker marker) {
        LayoutInflater inflater2  = LayoutInflater.from(getActivity());
        final  View view2 = inflater2.inflate(R.layout.ruqinjiankong,null);
        final  LatLng latLng2 = marker.getPosition();
        InfoWindow window2 = new InfoWindow(view2,latLng2,-47);
        baiduMap.showInfoWindow(window2);

    }

    private void showWarnningInfoWindows(Marker marker) {
        LayoutInflater inflater1 = LayoutInflater.from(getActivity());
        final  View view1 = inflater1.inflate(R.layout.warnning,null);
        warnningbutton = (Button) view1.findViewById(R.id.button_warning);
        final  LatLng latlng1 = marker.getPosition();
        InfoWindow window1  = new InfoWindow(view1,latlng1,-47);
        baiduMap.showInfoWindow(window1);
        warnningbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), WarnningActivity.class);
                startActivity(intent1);
            }
        });
    }

    private void showLightsInfoWindows(Marker marker) {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final  View view = inflater.inflate(R.layout.zhaoming,null);
        zhaomingbutton = (Button) view.findViewById(R.id.zhaomingbutton);
        final  LatLng latlng = marker.getPosition();
        InfoWindow window  = new InfoWindow(view,latlng,-47);
        baiduMap.showInfoWindow(window);
        zhaomingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.setClass(getActivity(), ZhaoMingActivity.class);
                startActivity(intent);
            }
        });

    }

    private void playVideo() {
        Intent intent  = new Intent();
        intent.setClass(getActivity(),VideoActivity.class);
        startActivity(intent);
    }

    //点击地图加上默认的marker
    private void setMapListener() {

        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //点击地图加上图标
//                baiduMap.clear();
//                setGroundOverlay();
                baiduMap.hideInfoWindow();

                Log.i("point","lat:"+latLng.latitude+","+"lng:"+latLng.longitude);

            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }
    //实现地图地位基础功能
    private class  myBDlocationListener implements BDLocationListener{


        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //得到经纬度
            mLongtitude = bdLocation.getLongitude();
            mLatitude= bdLocation.getLatitude();


            MyLocationData data = new MyLocationData.Builder().direction(1000)
                    .latitude(bdLocation.getLatitude()).longitude(bdLocation.getLongitude()).build();
            baiduMap.setMyLocationData(data);

            if (baiduMap.getMapStatus().zoom>=17){

                MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,descriptor);
                baiduMap.setMyLocationConfigeration(config);
            }else{
                MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,null);
                baiduMap.setMyLocationConfigeration(config);
            }
            OverlayOptions options  = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.line)).position(new LatLng(25.192301115436, 102.957196977816)).zIndex(10)
                    .alpha(0.5f);
//            baiduMap.addOverlay(options);
//            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(new LatLng(25.192307, 102.957196));
//            baiduMap.setMapStatus(msu);
//            LatLng southwest = new LatLng(25.19353856805835,102.95237802583497);
//            LatLng northeast = new LatLng(25.192843784614455,102.95825294386829);
//            LatLngBounds latLngBounds = new LatLngBounds.Builder()
//                    .include(southwest)
//                    .include(northeast)
//                    .build();
//            BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.drawable.line);
//            OverlayOptions optionsgroung = new GroundOverlayOptions()
//                    .positionFromBounds(latLngBounds)
//                    .image(descriptor)
//                    .transparency(0.8f)
//
//                    ;
//            baiduMap.addOverlay(optionsgroung);
//            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngBounds(latLngBounds);
//           baiduMap.setMapStatus(msu);
            setGroundOverlay();
            if(isFirstIn){
                LatLng latlng = new LatLng(25.192301115436, 102.957196977816);
                MapStatusUpdate MSU = MapStatusUpdateFactory.newLatLng(latlng);
                baiduMap.animateMapStatus(MSU);
                isFirstIn = false;
            }





        }
    }
    //放置地图覆盖物，能够跟随地图进行同步放缩，并且能够进行多点包含
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
