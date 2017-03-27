package com.example.melificent.myqianqi.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
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

import com.example.melificent.myqianqi.Bean.QueryTrainInfoByStation.TrainInfoByStationResultList;
import com.example.melificent.myqianqi.Bean.Train.TrainInfoResult;
import com.example.melificent.myqianqi.Presenter.IGetTrainInfoByStationPresenter;
import com.example.melificent.myqianqi.Presenter.IGetTrainInfoByTrainNamePresenter;
import com.example.melificent.myqianqi.Presenter.Impl.IGetTrainInfoByStationPresenterImpl;
import com.example.melificent.myqianqi.Presenter.Impl.IGetTrainInfoByTrainNamePresenterImpl;
import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.Utils.TimeFormatUtils;
import com.example.melificent.myqianqi.View.GetTrainInfoByStation;
import com.example.melificent.myqianqi.View.GetTrainInfoByTrainName;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/22.
 */

public class TrainQueryActivity extends AppCompatActivity implements GetTrainInfoByStation,GetTrainInfoByTrainName {
    @InjectView(R.id.btn_train_bystation)
    Button btn_train_bystation;
    @InjectView(R.id.btn_train_byname)
    Button btn_train_byname;
    @InjectView(R.id.train_strat)
    EditText start;
    @InjectView(R.id.train_end)
    EditText end;
    @InjectView(R.id.train_nextDate)
    TextView nextDate;
    @InjectView(R.id.train_query)
    Button query;
    @InjectView(R.id.train_name_field)
    LinearLayout name_field;
    @InjectView(R.id.train_station_field)
    LinearLayout station_field;
    @InjectView(R.id.train_name)
    EditText train_name;
    @InjectView(R.id.trainquerygoback)
    ImageView back;
    @InjectView(R.id.train_exchange)
    ImageView exchange;
    @InjectView(R.id.train_date_more)
    ImageView more;
    @InjectView(R.id.train_station_tororrow)
    TextView tomorrow;
    IGetTrainInfoByStationPresenter presenter = new IGetTrainInfoByStationPresenterImpl(this);
    IGetTrainInfoByTrainNamePresenter presenter_byname = new IGetTrainInfoByTrainNamePresenterImpl(this);
    boolean isFirstLoad =true;
    String startStation;
    String endStation;
    private PopupWindow popupWindow;
    String nowDate;
    Date DATE;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainquerybystationactivity);
        ButterKnife.inject(this);
        setButtonListener();
        setTime();
//        ExchangeDesitinationAndStartStation();
    }

    private void ExchangeDesitinationAndStartStation() {
        startStation = start.getText().toString().trim();
        endStation = end.getText().toString().trim();
        exchange.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String temp;
               temp = startStation;
                endStation = temp;
                startStation = endStation;
            }
        });
    }

    private void setTime() {
        Date date = new Date();
        DATE =date;
//        Calendar calendar = new GregorianCalendar();
//        calendar.setTime(date);
//        calendar.add(calendar.DATE,1);
//        date= calendar.getTime();
//        String finalTime = TimeFormatUtils.formatDate(date).substring(5,7)+"月"+TimeFormatUtils.formatDate(date).substring(8)+"日";
        nextDate.setText(TimeFormatUtils.formatDate(date).substring(6,7)+"月"+TimeFormatUtils.formatDate(date).substring(8)+"日");
        nowDate = TimeFormatUtils.formatDate(date);
    }

    private void setButtonListener() {
        tomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.add(calendar.DATE,1);
                date= calendar.getTime();
                DATE = date;
                nowDate = TimeFormatUtils.formatDate(date);
                nextDate.setText(nowDate.substring(6,7)+"月"+nowDate.substring(8)+"日");
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
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
                    nowDate = TimeFormatUtils.formatDate(date);
                    data[i] = nowDate;
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
                        DATE = date;
                        nowDate = TimeFormatUtils.formatDate(date);
                        nextDate.setText(nowDate.substring(6,7)+"月"+nowDate.substring(8)+"日");
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
        btn_train_byname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_train_byname.setBackgroundColor(Color.parseColor("#1eb6b7"));
                btn_train_byname.setTextColor(Color.parseColor("#ffffff"));
                btn_train_byname.setText("按名称");
                btn_train_bystation.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_train_bystation.setTextColor(Color.parseColor("#1eb6b7"));
                btn_train_bystation.setText("按发到站");
                name_field.setVisibility(View.VISIBLE);
                station_field.setVisibility(View.GONE);
                isFirstLoad = false;
                query.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (train_name.getText().toString().trim() != null){
                            presenter_byname.IGetTrainInfoByTrainNamePresenter(train_name.getText().toString().trim());
                        }else{
                            Toast.makeText(TrainQueryActivity.this, "车次不能为空!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        btn_train_bystation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_train_byname.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_train_byname.setTextColor(Color.parseColor("#1eb6b7"));
                btn_train_byname.setText("按名称");
                btn_train_bystation.setBackgroundColor(Color.parseColor("#1eb6b7"));
                btn_train_bystation.setTextColor(Color.parseColor("#ffffff"));
                btn_train_bystation.setText("按发到站");
                name_field.setVisibility(View.GONE);
                station_field.setVisibility(View.VISIBLE);
                isFirstLoad = false;
                query.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startStation = start.getText().toString().trim();
                        endStation = end.getText().toString().trim();
                        if(startStation != null && endStation != null){
                            presenter.getTrainInfoByStation(startStation,endStation,nowDate);
                        }else {
                            Toast.makeText(TrainQueryActivity.this, "始发地或目的地不能为空!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        if (isFirstLoad){
            query.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startStation = start.getText().toString().trim();
                    endStation = end.getText().toString().trim();
                    if(startStation != null && endStation != null){
                        presenter.getTrainInfoByStation(startStation,endStation,nowDate);
                    }else {
                        Toast.makeText(TrainQueryActivity.this, "始发地或目的地不能为空!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    public void getTrainInfoByStationSuccess(List<TrainInfoByStationResultList> lists) {
            if (lists.size() != 0){
                Intent intent = new Intent(TrainQueryActivity.this,TrainQueryResultActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("trainResult", (Serializable) lists);
                b.putString("start",start.getText().toString().trim());
                b.putString("end",end.getText().toString().trim());
                intent.putExtras(b);
                intent.putExtra("date",DATE.getTime());
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, "查询失败，请确认后重试!", Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public void getTrainInfoByStationFail(String Msg) {
        Toast.makeText(this, "查询失败，请确认后重试!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getTrainInfoByTrainNameSuccess(TrainInfoResult results) {
        Intent intent = new Intent(TrainQueryActivity.this,TrainQueryResultByNameActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("TrainInfoResult",  results);

        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    @Override
    public void getTrainInfoByTrainNameFaile(String error_Msg) {
        Toast.makeText(this, "查询列车信息失败！", Toast.LENGTH_SHORT).show();
    }
}
