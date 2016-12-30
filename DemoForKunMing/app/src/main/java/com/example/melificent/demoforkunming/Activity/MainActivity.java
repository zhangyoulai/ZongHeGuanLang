package com.example.melificent.demoforkunming.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.melificent.demoforkunming.Fragment.MapFragment;
import com.example.melificent.demoforkunming.Fragment.MessageFragment;
import com.example.melificent.demoforkunming.Fragment.ModifySearchFragment;
import com.example.melificent.demoforkunming.Fragment.OprationFragment;
import com.example.melificent.demoforkunming.Fragment.SearchFragmnet;
import com.example.melificent.demoforkunming.R;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;
/*
MainActivity中主要是实现了fragment和viewpager之间的联动
通过点击或者滑动到每一个fragment中实现标题栏的切换
 */

public class MainActivity extends AppCompatActivity {
private RadioGroup radioGroup;
    private RadioButton map;
    private RadioButton search;
    private  RadioButton opration;
    private RadioButton message;
//    TextView textView;
    private  FragmentManager fmanager;
    private FragmentTransaction ftransaction;

    private ViewPager viewPager;
    List<Fragment> fragments;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化主界面控件
        initViews();
        //给viewpager设置适配器
        setAdapter();
        //完成view pager和Fragment之间的联动
        setListener();
        Intent intent  = getIntent();
        intent.getIntExtra("page",0);
        //百度地图的版本控制
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }


        }






    private void setListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.map:

                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.search:

                        viewPager.setCurrentItem(1);
//                        textView.setVisibility(View.GONE);
                        break;
                    case  R.id.opration:
                        viewPager.setCurrentItem(2);
//                        textView.setVisibility(View.GONE);
                        break;
                    case R.id.message:
                        viewPager.setCurrentItem(3);


                }
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        map.setChecked(true);

                        break;
                    case 1:
                        //隐藏主标题栏，显示自己特有的标题栏
                        search.setChecked(true);
//                        textView.setVisibility(View.GONE);
                        break;
                    case 2:
                     //隐藏主标题栏，显示自己特有的标题栏
                        opration.setChecked(true);
//                        textView.setVisibility(View.GONE);
                        break;
                    case 3:
                        message.setChecked(true);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViews() {
        map = (RadioButton) findViewById(R.id.map);
        search = (RadioButton) findViewById(R.id.search);
        opration = (RadioButton) findViewById(R.id.opration);
        message = (RadioButton) findViewById(R.id.message);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
//        textView = (TextView) findViewById(R.id.textview);
    }
    private void setAdapter(){
        fragments = new ArrayList();
        fragments.add(new MapFragment());
        fragments.add(new ModifySearchFragment());
        fragments.add(new OprationFragment());
        fragments.add(new MessageFragment());
        adapter = new MainAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

    }

    public void gotoOprationFragment() {
        fmanager = getSupportFragmentManager();
        ftransaction = fmanager.beginTransaction();
        ftransaction.replace(R.id.viewpager,new OprationFragment());
        ftransaction.commit();
    }

    private class MainAdapter extends FragmentPagerAdapter {
        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        textView.setVisibility(View.GONE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        textView.setVisibility(View.GONE);
    }
}
