package com.example.melificent.myqianqi.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.melificent.myqianqi.Bean.AirPlaneBeanByName.AirPlaneInfoResult;
import com.example.melificent.myqianqi.Bean.AirPlaneBeanByStation.AirPlaneInfoByStationResult;
import com.example.melificent.myqianqi.Presenter.IGetAirPlaneInfoByNamePresenter;
import com.example.melificent.myqianqi.Presenter.IGetAirPlaneInfoByStationPresenter;
import com.example.melificent.myqianqi.Presenter.Impl.IGetAirPlaneByNamePresenterImpl;
import com.example.melificent.myqianqi.Presenter.Impl.IGetAirPlaneInfoByStationPresenterImpl;
import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.Utils.TimeFormatUtils;
import com.example.melificent.myqianqi.View.GetAirPlaneInfoByName;
import com.example.melificent.myqianqi.View.GetAirPlaneInfoByStation;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/21.
 */

public class AirPlaneQueryActivity extends AppCompatActivity implements GetAirPlaneInfoByStation,GetAirPlaneInfoByName {
    @InjectView(R.id.btn_plane_name)
    TextView btn_plane_name;
    @InjectView(R.id.btn_plane_station)
    TextView btn_plane_station;
    @InjectView(R.id.plane_strat)
    EditText satrt;
    @InjectView(R.id.plane_end)
    EditText end;
    @InjectView(R.id.plane_query)
    Button plane_query;
    @InjectView(R.id.plane_nowDate)
    TextView nowDate;
    @InjectView(R.id.airplane_bystation_field)
    LinearLayout airplane_bystation_field;
    @InjectView(R.id.airplane_name_field)
    LinearLayout airplane_name_field;
    @InjectView(R.id.plane_name)
    EditText plane_name;
    @InjectView(R.id.plane_byname_nextDate)
    TextView tomorrow;
    @InjectView(R.id.plane_byname_nowDate)
    TextView nowDate_byname;
    @InjectView(R.id.airplanequerygoback)
    ImageView back;
    @InjectView(R.id.plane_date_more)
    ImageView date_more;
    @InjectView(R.id.plane_station_tomorrow)
    TextView station_tomorrow;
    @InjectView(R.id.plane_name_more)
            ImageView name_date_more;
    boolean isFirstLoad = true;
    String NowDate;
    String NowdateStation;
    Date DATE_Station;
    Date DATE_Name;
    private PopupWindow popupWindow;
    IGetAirPlaneInfoByNamePresenter presenter_byname = new IGetAirPlaneByNamePresenterImpl(this);

    IGetAirPlaneInfoByStationPresenter presenter = new IGetAirPlaneInfoByStationPresenterImpl(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.airplanequery);
        ButterKnife.inject(this);
        setButtonListener();
        setTime();



    }

    private void setTime() {
        Date date = new Date();
        DATE_Station = date;
        Date date1 = new Date();
        DATE_Name = date1;
        NowDate = TimeFormatUtils.formatDate(date);
        nowDate.setText(NowDate.substring(6,7)+"月"+NowDate.substring(8)+"日");
        nowDate_byname.setText(NowDate.substring(6,7)+"月"+NowDate.substring(8)+"日");
        NowdateStation = TimeFormatUtils.formatDate(date);
    }

    private void setButtonListener() {
        name_date_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.express_pop,null);
                popupWindow = new PopupWindow(view , LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT,true);
                popupWindow.setAnimationStyle(R.style.AnimationFade);
                popupWindow.showAtLocation(v, Gravity.RIGHT,0,0);
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

                ListView listView = (ListView) view.findViewById(R.id.express_listview);
                String []data= new String[15];
                for (int i =0;i<15;i++){
                    Date date = new Date();
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(date);
                    calendar.add(calendar.DATE,i);
                    date= calendar.getTime();
                    NowDate = TimeFormatUtils.formatDate(date);
                    data[i] = NowDate;
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(v.getContext(),android.R.layout.simple_list_item_1,data);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Date date = new Date();
                        Calendar calendar = new GregorianCalendar();
                        calendar.setTime(date);
                        calendar.add(calendar.DATE,position);
                        date= calendar.getTime();
                        DATE_Name = date;
                        NowDate = TimeFormatUtils.formatDate(date);
                        nowDate_byname.setText(NowDate.substring(6,7)+"月"+NowDate.substring(8)+"日");
                        if (popupWindow!=null&&popupWindow.isShowing()){
                            popupWindow.dismiss();
                            popupWindow=null;
                        }
                    }
                });
            }
        });
        station_tomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.add(calendar.DATE,1);
                date= calendar.getTime();
                DATE_Station = date;
                NowdateStation = TimeFormatUtils.formatDate(date);
                nowDate.setText(NowdateStation.substring(6,7)+"月"+NowdateStation.substring(8)+"日");

            }
        });
        date_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.express_pop,null);
                popupWindow = new PopupWindow(view , LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT,true);
                popupWindow.setAnimationStyle(R.style.AnimationFade);
                popupWindow.showAtLocation(v, Gravity.RIGHT,0,0);
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

                ListView listView = (ListView) view.findViewById(R.id.express_listview);
                String []data= new String[15];
                for (int i =0;i<15;i++){
                    Date date = new Date();
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(date);
                    calendar.add(calendar.DATE,i);
                    date= calendar.getTime();
                    NowdateStation = TimeFormatUtils.formatDate(date);
                    data[i] = NowdateStation;
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(v.getContext(),android.R.layout.simple_list_item_1,data);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Date date = new Date();
                        Calendar calendar = new GregorianCalendar();
                        calendar.setTime(date);
                        calendar.add(calendar.DATE,position);
                        date= calendar.getTime();
                        DATE_Station =date;
                        NowdateStation = TimeFormatUtils.formatDate(date);
                        nowDate.setText(NowdateStation.substring(6,7)+"月"+NowdateStation.substring(8)+"日");
                        if (popupWindow!=null&&popupWindow.isShowing()){
                            popupWindow.dismiss();
                            popupWindow=null;
                        }
                    }
                });

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.add(calendar.DATE,1);
                date= calendar.getTime();
                DATE_Name = date;
                NowDate = TimeFormatUtils.formatDate(date);
                nowDate_byname.setText(NowDate.substring(6,7)+"月"+NowDate.substring(8)+"日");
            }
        });
        btn_plane_name.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            btn_plane_name.setBackgroundColor(Color.parseColor("#1eb6b7"));
            btn_plane_name.setTextColor(Color.parseColor("#ffffff"));
            btn_plane_station.setTextColor(Color.parseColor("#1eb6b7"));
            btn_plane_station.setBackgroundColor(Color.parseColor("#ffffff"));
            airplane_bystation_field.setVisibility(View.GONE);
            airplane_name_field.setVisibility(View.VISIBLE);
            plane_query.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (plane_name.getText().toString().trim() !=null ){
                        presenter_byname.getAirPlaneInfoByNamePresenter(plane_name.getText().toString().trim(),NowDate);
                        Log.i("planetimebyname", "onClick: "+NowDate);
                        Log.i("planename", "onClick: "+plane_name.getText().toString());
                    }else {
                        Toast.makeText(AirPlaneQueryActivity.this, "航班名称不能为空!", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    });
        btn_plane_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_plane_name.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_plane_name.setTextColor(Color.parseColor("#1eb6b7"));
                btn_plane_station.setTextColor(Color.parseColor("#ffffff"));
                btn_plane_station.setBackgroundColor(Color.parseColor("#1eb6b7"));
                airplane_bystation_field.setVisibility(View.VISIBLE);
                airplane_name_field.setVisibility(View.GONE);
                plane_query.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (satrt.getText().toString().trim() !=null && end.getText().toString().trim()!= null ){
                            presenter.getAirPlaneInfoByStation(satrt.getText().toString().trim(),end.getText().toString().trim(),NowdateStation);
                        }else {
                            Toast.makeText(AirPlaneQueryActivity.this, "起点站或终点站不能为空!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        if (isFirstLoad){
            plane_query.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (satrt.getText().toString().trim() !=null && end.getText().toString().trim()!= null ){
                        presenter.getAirPlaneInfoByStation(satrt.getText().toString().trim(),end.getText().toString().trim(),NowdateStation);
                    }else {
                        Toast.makeText(AirPlaneQueryActivity.this, "起点站或终点站不能为空!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }

    @Override
    public void getAirPlaneInfoByStationSuccess(List<AirPlaneInfoByStationResult> results) {
            if (results.size() !=0){
                Intent intent = new Intent(AirPlaneQueryActivity.this,QueryPlaneResultActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("airResult", (Serializable) results);
                b.putString("start",satrt.getText().toString().trim());
                b.putString("end",end.getText().toString().trim());
                intent.putExtras(b);
                intent.putExtra("dateStation",DATE_Station.getTime());
                Log.i("date_station", "getAirPlaneInfoByStationSuccess: "+TimeFormatUtils.formatDate(DATE_Station));
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, "查询出错,请核对后重试", Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public void getAirPlaneInfoByStationFail(String error_Msg) {
        Toast.makeText(this, "查询出错,请核对后重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getAirPlaneInfoByNameSuccess(AirPlaneInfoResult result) {
        Intent intent = new Intent(AirPlaneQueryActivity.this,AirplaneQueryNameResultActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("airplaneName",result);
        intent.putExtras(b);
        intent.putExtra("dateName",DATE_Name.getTime());
        intent.putExtra("name",plane_name.getText().toString().trim());
        startActivity(intent);
        finish();
    }

    @Override
    public void getAirPlaneInfoByNameFail(String error_Msg) {
        Log.i("planequeryfail", "getAirPlaneInfoByNameFail: "+error_Msg);
        Toast.makeText(this, "查询出错,请核对后重试", Toast.LENGTH_SHORT).show();

    }
}
