package com.example.melificent.myqianqi.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.melificent.myqianqi.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/21.
 */

public class FrameActivity extends AppCompatActivity {
    @InjectView(R.id.city_fragment)
    View city_Fragment;
    @InjectView(R.id.my_home)
    View my_home;
    @InjectView(R.id.myhome_title)
    TextView myhome_title;
    @InjectView(R.id.city_title)
    TextView city_title;
    @InjectView(R.id.btn_city)
    ImageButton btn_city;
    @InjectView(R.id.btn_home)
    ImageButton btn_home;
    @InjectView(R.id.home_linearlayout)
    LinearLayout home_linearlayout;
    @InjectView(R.id.city_linearlayout)
    LinearLayout city_linearlayout;
    boolean isFirstLoad = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.inject(this);
        setImageButtonListener();
        initViewsSetting();
    }

    private void initViewsSetting() {
        city_Fragment.setVisibility(View.VISIBLE);
        my_home.setVisibility(View.GONE);
        if (isFirstLoad){
            btn_home.setBackgroundResource(R.drawable.jiayuan_bottom_hui);
        }

    }

    private void setImageButtonListener() {
        home_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                city_Fragment.setVisibility(View.GONE);
                my_home.setVisibility(View.VISIBLE);
                btn_city.setBackgroundResource(R.drawable.chengshi_bottom_hui);
                city_title.setTextColor(Color.parseColor("#a6a6a6"));

                btn_home.setBackgroundResource(R.drawable.jiayuan_bottom);
                myhome_title.setTextColor(Color.parseColor("#1eb6b7"));
                isFirstLoad = false;
            }
        });
       city_linearlayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               city_Fragment.setVisibility(View.VISIBLE);
               my_home.setVisibility(View.GONE);
               btn_city.setBackgroundResource(R.drawable.chengshi_bottom);
               city_title.setTextColor(Color.parseColor("#1eb6b7"));
               btn_home.setBackgroundResource(R.drawable.jiayuan_bottom_hui);
               myhome_title.setTextColor(Color.parseColor("#a6a6a6"));
               isFirstLoad= false;
           }
       });

    }
}
