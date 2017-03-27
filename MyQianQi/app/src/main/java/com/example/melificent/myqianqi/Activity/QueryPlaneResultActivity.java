package com.example.melificent.myqianqi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.melificent.myqianqi.Adapter.Plane_byStation_Adapter;
import com.example.melificent.myqianqi.Bean.AirPlaneBeanByStation.AirPlaneInfoByStationResult;
import com.example.melificent.myqianqi.Presenter.IGetAirPlaneInfoByStationPresenter;
import com.example.melificent.myqianqi.Presenter.Impl.IGetAirPlaneInfoByStationPresenterImpl;
import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.Utils.TimeFormatUtils;
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

public class QueryPlaneResultActivity extends AppCompatActivity implements GetAirPlaneInfoByStation {
    @InjectView(R.id.plane_end_station)
    TextView end;
    @InjectView(R.id.plane_start_station)
    TextView start;
    @InjectView(R.id.plane_result_bystation)
    RecyclerView recyclerView;
    @InjectView(R.id.plane_bystation_date)
    TextView date;
    @InjectView(R.id.planestationgoback)
    ImageView back;
    @InjectView(R.id.plane_station_result_calendar)
            ImageView calendar;
    @InjectView(R.id.plane_result_station_datebefore)
            LinearLayout datebefore;
    @InjectView(R.id.plane_bystation_result_next)
            LinearLayout date_next;
    List<AirPlaneInfoByStationResult> results;
    Date DATE;
    String Start;
    String End;
    private PopupWindow popupWindow;
    IGetAirPlaneInfoByStationPresenter presenter = new IGetAirPlaneInfoByStationPresenterImpl(this);

    Plane_byStation_Adapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_plane_result);
        ButterKnife.inject(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getData();
        setTextForTextview();
       setButtonListener();
        adapter = new Plane_byStation_Adapter(results);
        recyclerView.setAdapter(adapter);



    }

    private void setButtonListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        datebefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(DATE);
                calendar.add(calendar.DATE,-1);
                Date date1 = new Date();
                date1= calendar.getTime();
                Log.i("planestationdatebefore", "onClick: "+TimeFormatUtils.formatDate(date1));
                date.setText(TimeFormatUtils.formatDate(date1).substring(6,7)+"月"+TimeFormatUtils.formatDate(date1).substring(8)+"日");
                presenter.getAirPlaneInfoByStation(Start,End,TimeFormatUtils.formatDate(date1));
            }
        });
        date_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(DATE);
                calendar.add(calendar.DATE,1);
                Date date1 = new Date();
                date1= calendar.getTime();
                Log.i("planestationdatenext", "onClick: "+TimeFormatUtils.formatDate(date1));
                date.setText(TimeFormatUtils.formatDate(date1).substring(6,7)+"月"+TimeFormatUtils.formatDate(date1).substring(8)+"日");
                presenter.getAirPlaneInfoByStation(Start,End,TimeFormatUtils.formatDate(date1));
            }
        });
        calendar.setOnClickListener(new View.OnClickListener() {
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
                    String nowDate = TimeFormatUtils.formatDate(date);
                    data[i] = nowDate;
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(v.getContext(),android.R.layout.simple_list_item_1,data);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Date date1 = new Date();
                        Calendar calendar = new GregorianCalendar();
                        calendar.setTime(date1);
                        calendar.add(calendar.DATE,position);
                        date1= calendar.getTime();
                        String nowDate = TimeFormatUtils.formatDate(date1);
                        date.setText(nowDate.substring(6,7)+"月"+nowDate.substring(8)+"日");
                        presenter.getAirPlaneInfoByStation(Start,End,TimeFormatUtils.formatDate(date1));
                        if (popupWindow!=null&&popupWindow.isShowing()){
                            popupWindow.dismiss();
                            popupWindow=null;
                        }
                    }
                });
            }
        });
    }

    private void setTextForTextview() {
        date.setText(results.get(0).FlightDate.substring(5,7)+"月"+results.get(0).FlightDate.substring(8)+"日");
        end.setText(results.get(0).ArrCity);
        start.setText(results.get(0).DepCity);
    }

    private void getData() {
        Intent intent = getIntent();
        results = (List<AirPlaneInfoByStationResult>) intent.getSerializableExtra("airResult");
        DATE = new Date(intent.getLongExtra("dateStation",0));
        Log.i("getDATE", "getData: "+TimeFormatUtils.formatDate(DATE));
        Start = intent.getStringExtra("start");
        End = intent.getStringExtra("end");
    }


    @Override
    public void getAirPlaneInfoByStationSuccess(List<AirPlaneInfoByStationResult> results) {
        adapter = new Plane_byStation_Adapter(results);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getAirPlaneInfoByStationFail(String error_Msg) {
        Toast.makeText(this, "刷新失败！请稍后重试", Toast.LENGTH_SHORT).show();
    }
}
