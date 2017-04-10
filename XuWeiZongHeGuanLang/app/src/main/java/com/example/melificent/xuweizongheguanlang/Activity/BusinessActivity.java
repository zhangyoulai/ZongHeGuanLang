package com.example.melificent.xuweizongheguanlang.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.example.melificent.xuweizongheguanlang.Adapter.GrideAdapter;
import com.example.melificent.xuweizongheguanlang.Bean.GrideBean;
import com.example.melificent.xuweizongheguanlang.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/2/13.
 * Manage company contains Business,Company,Analysis and so on .........
 */

public class BusinessActivity extends AppCompatActivity {
    @InjectView(R.id.grideview)
    GridView gridView;
    @InjectView(R.id.analysis)
    Button analysis;
    @InjectView(R.id.business_back)
    Button back;
    @InjectView(R.id.business_input)
    EditText input;

    List<GrideBean> beans;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        ButterKnife.inject(this);
        initConfigForGrideView();
        setAdapterforGrideView();
        setItemClickListenerForGrideView();
    }

    private void setItemClickListenerForGrideView() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(BusinessActivity.this,CompanyManage.class));
                        break;
                    case 1:
                        startActivity(new Intent(BusinessActivity.this,GasActivity.class));
                        break;
                }
            }
        });
    }

    private void setAdapterforGrideView() {
      gridView.setAdapter(new GrideAdapter(beans,this));
    }

    private void initConfigForGrideView() {
        beans = new ArrayList<>();
        int[] bitmap= new int[]{
           R.drawable.rulangqiye,R.drawable.danganchanxun, R.drawable.shebeichanxun,R.drawable.beijianchaxun,
                R.drawable.rulangfeiyongchaxun,R.drawable.chengbenchaxun
        } ;
        String[] describle  = new String[]{
                "入廊企业","档案查询","设备查询","备件查询","入廊费用查询","成本查询"
        };
        for (int i= 0;i<6;i++){
         beans.add(new GrideBean(describle[i],BitmapFactory.decodeResource(this.getResources(),bitmap[i])));
        }
    }


}
