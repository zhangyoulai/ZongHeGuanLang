package com.example.melificent.xuweizongheguanlang.Activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.melificent.xuweizongheguanlang.Fragment.MapFragment;
import com.example.melificent.xuweizongheguanlang.Fragment.SearchFragment;
import com.example.melificent.xuweizongheguanlang.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/1/12.
 */

public class OprationActivity extends AppCompatActivity {
    @InjectView(R.id.map)
    RadioButton map;
    @InjectView(R.id.search)
    RadioButton search;
    @InjectView(R.id.radiogroup)
    RadioGroup radioGroup;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    List<Fragment> fragmentList = new ArrayList<>();
    MainAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.opration_activity);
        ButterKnife.inject(this);
        //联动viewpager和Radiogroup
        setLinkageForViewpagerAndRadioGroup();
        //给viewpager填充fragment
        setContentForViewpager();
        //百度地图的版本控制
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

    }
    }

    private void setContentForViewpager() {
        fragmentList.add(new MapFragment());
        fragmentList.add(new SearchFragment());
        adapter = new MainAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);

    }

    private void setLinkageForViewpagerAndRadioGroup() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.map:
                        viewpager.setCurrentItem(0);
                        break;
                    case  R.id.search:
                        viewpager.setCurrentItem(1);
                        break;

                }
            }
        });
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                        search.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class MainAdapter extends FragmentPagerAdapter {
        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
