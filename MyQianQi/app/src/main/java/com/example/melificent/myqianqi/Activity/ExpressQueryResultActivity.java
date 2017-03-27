package com.example.melificent.myqianqi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.melificent.myqianqi.Adapter.ExpressAdapter;
import com.example.melificent.myqianqi.Bean.Express.ExpressList;
import com.example.melificent.myqianqi.Bean.Express.ExpressResult;
import com.example.melificent.myqianqi.R;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/22.
 */

public class ExpressQueryResultActivity extends AppCompatActivity {
    @InjectView(R.id.express_recycleview)
    RecyclerView recyclerView;
    @InjectView(R.id.express_companyname)
    TextView company;
    @InjectView(R.id.expressNo)
    TextView No;
    @InjectView(R.id.expressresultgoback)
    ImageView back;
    ExpressResult result;
    ExpressAdapter adapter;
    List<ExpressList> expresslist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expressrsultactivity);
        ButterKnife.inject(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getData();
        setTextForTextview();
        setAdapterForRecycleview();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setAdapterForRecycleview() {
        adapter = new ExpressAdapter(expresslist);
        recyclerView.setAdapter(adapter);
    }

    private void setTextForTextview() {
        company.setText(result.company);
        No.setText(result.no);
    }

    private void getData() {
        Intent intent = getIntent();
        result = (ExpressResult) intent.getSerializableExtra("express");
        expresslist = result.list;
        Collections.reverse(expresslist);
        Log.i("remark", "getDataremark: "+expresslist.get(0).remark);

    }
}
