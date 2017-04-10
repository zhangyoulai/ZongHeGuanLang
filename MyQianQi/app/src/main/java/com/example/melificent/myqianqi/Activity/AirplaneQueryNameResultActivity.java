package com.example.melificent.myqianqi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.example.melificent.myqianqi.Adapter.Plane_byName_Adapter;
import com.example.melificent.myqianqi.Bean.AirPlaneBeanByName.AirPlaneInfoList;
import com.example.melificent.myqianqi.Bean.AirPlaneBeanByName.AirPlaneInfoResult;
import com.example.melificent.myqianqi.Presenter.IGetAirPlaneInfoByNamePresenter;
import com.example.melificent.myqianqi.Presenter.Impl.IGetAirPlaneByNamePresenterImpl;
import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.Utils.TimeFormatUtils;
import com.example.melificent.myqianqi.View.GetAirPlaneInfoByName;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/23.
 */

public class AirplaneQueryNameResultActivity extends AppCompatActivity implements GetAirPlaneInfoByName {
    @InjectView(R.id.airplane_start_station_byname)
    TextView start;
    @InjectView(R.id.airplane_end_station_byname)
    TextView end;
    @InjectView(R.id.airplane_company_name)
    TextView company_name;
    @InjectView(R.id.airplane_start_byname)
    TextView airplnae_start;
    @InjectView(R.id.airplane_endstation_byname)
    TextView airplane_end;
    @InjectView(R.id.airplane_startTime_byname)
    TextView airplane_startTime;
    @InjectView(R.id.airplane_endTime_byname)
    TextView airplane_endTime;
    @InjectView(R.id.airplane_name)
    TextView airplane_name;
    @InjectView(R.id.airplane_status)
    TextView airplane_status;
    @InjectView(R.id.airplane_result_byname)
    RecyclerView recyclerView;
    @InjectView(R.id.airplane_byname_date)
    TextView airplane_byname_date;
    @InjectView(R.id.airplaneresultbynamegoback)
    ImageView back;
    @InjectView(R.id.plane_name_result_datenext)
    LinearLayout datenext;
    @InjectView(R.id.plane_name_result_datebefore)
    LinearLayout datebefore;
    @InjectView(R.id.plane_name_calendar)
    ImageView calendar;
    AirPlaneInfoResult result;
    String start_weather;
    String end_weather;
    String pass_weather;
    String Name;
    Plane_byName_Adapter adapter;
    List<AirPlaneInfoList> list;
    Date DATE;
    private PopupWindow popupWindow;

    IGetAirPlaneInfoByNamePresenter presenter_byname = new IGetAirPlaneByNamePresenterImpl(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plane_name_result);
        ButterKnife.inject(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getData();
        setAdapterForRecycleview();
        setTextForTextview();
        setButtonListener();

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
                calendar.add(calendar.DATE, -1);
                Date date1 = new Date();
                date1 = calendar.getTime();
                airplane_byname_date.setText(TimeFormatUtils.formatDate(date1).substring(6, 7) + "月" + TimeFormatUtils.formatDate(date1).substring(8) + "日");
                presenter_byname.getAirPlaneInfoByNamePresenter(Name, TimeFormatUtils.formatDate(date1));
            }
        });
        datenext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(DATE);
                calendar.add(calendar.DATE, 1);
                Date date1 = new Date();
                date1 = calendar.getTime();
                airplane_byname_date.setText(TimeFormatUtils.formatDate(date1).substring(6, 7) + "月" + TimeFormatUtils.formatDate(date1).substring(8) + "日");
                presenter_byname.getAirPlaneInfoByNamePresenter(Name, TimeFormatUtils.formatDate(date1));
            }
        });
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.express_pop, null);
                popupWindow = new PopupWindow(view, LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT, true);
                popupWindow.setAnimationStyle(R.style.AnimationFade);
                popupWindow.showAtLocation(v, Gravity.RIGHT, 0, 0);
                view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                            popupWindow = null;
                        }
                        return false;
                    }
                });

                ListView listView = (ListView) view.findViewById(R.id.express_listview);
                String[] data = new String[15];
                for (int i = 0; i < 15; i++) {
                    Date date = new Date();
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(date);
                    calendar.add(calendar.DATE, i);
                    date = calendar.getTime();
                    String nowDate = TimeFormatUtils.formatDate(date);
                    data[i] = nowDate;
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(v.getContext(), android.R.layout.simple_list_item_1, data);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Date date1 = new Date();
                        Calendar calendar = new GregorianCalendar();
                        calendar.setTime(date1);
                        calendar.add(calendar.DATE, position);
                        date1 = calendar.getTime();
                        String nowDate = TimeFormatUtils.formatDate(date1);
                        airplane_byname_date.setText(nowDate.substring(6, 7) + "月" + nowDate.substring(8) + "日");
                        presenter_byname.getAirPlaneInfoByNamePresenter(Name, nowDate);
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                            popupWindow = null;
                        }
                    }
                });
            }
        });

    }

    private void setTextForTextview() {
        start.setText(result.info.from_city);
        end.setText(result.info.to_city);
        airplnae_start.setText(result.info.from);
        airplane_end.setText(result.info.to);

        if (result.info.sjqftime.equals("")) {
            airplane_startTime.setText(result.info.qftime);
        } else {
            airplane_startTime.setText(result.info.sjqftime);

        }
        if (result.info.sjddtime.equals("")) {
            airplane_endTime.setText(result.info.ddtime);
        } else {
            airplane_endTime.setText(result.info.sjddtime);

        }

        airplane_byname_date.setText(result.info.date.substring(5, 7) + "月" + result.info.date.substring(8) + "日");
        airplane_name.setText(result.info.fno);
        airplane_status.setText("(" + result.info.state + ")");
        company_name.setText(result.info.company);

    }

    private void setAdapterForRecycleview() {
        adapter = new Plane_byName_Adapter(list, result.info.from_weather, result.info.to_weather, result.info.jingting_weater.weater);
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        Intent intent = getIntent();
        result = (AirPlaneInfoResult) intent.getSerializableExtra("airplaneName");
        Name = intent.getStringExtra("name");
        DATE = new Date(intent.getLongExtra("dateName", 0));
        list = result.list;
    }

    @Override
    public void getAirPlaneInfoByNameSuccess(AirPlaneInfoResult result) {
        if (result != null){
            adapter = new Plane_byName_Adapter(result.list, result.info.from_weather, result.info.to_weather, result.info.jingting_weater.weater);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }


    }
        @Override
        public void getAirPlaneInfoByNameFail (String error_Msg){


    }
}
