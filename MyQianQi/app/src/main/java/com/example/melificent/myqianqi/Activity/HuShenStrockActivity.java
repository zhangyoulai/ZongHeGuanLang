package com.example.melificent.myqianqi.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.melificent.myqianqi.Bean.ShanghaiAndShenzhenStrock.HuShenStrockResult;
import com.example.melificent.myqianqi.Presenter.IGetHuShenStrockPresenter;
import com.example.melificent.myqianqi.Presenter.Impl.IGetHuShenStrockPresenterImpl;
import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.View.GetHuShenStrockResult;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/17.
 */

public class HuShenStrockActivity extends AppCompatActivity implements GetHuShenStrockResult {
    @InjectView(R.id.radiogroup)
    RadioGroup radioGroup;
    @InjectView(R.id.shenzhen)
    RadioButton shenzhen;
    @InjectView(R.id.shanghai)
    RadioButton shanghai;
    @InjectView(R.id.xianggang)
    RadioButton Hongkong;
    @InjectView(R.id.meiguo)
    RadioButton American;
    @InjectView(R.id.gidNo)
    EditText gidNo;
    @InjectView(R.id.strock_query)
    Button query;
    IGetHuShenStrockPresenter presenter = new IGetHuShenStrockPresenterImpl(this);
    String GidNo1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.strock_activity);
        ButterKnife.inject(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.shanghai:
                        GidNo1 = "sh";
                        break;
                    case R.id.shenzhen:
                        GidNo1="sz";
                        break;
                    case R.id.meiguo:
                        GidNo1="";
                        break;
                    case R.id.xianggang:
                        GidNo1 ="";
                        break;
                }
            }
        });

        query.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String GidNo = GidNo1+ gidNo.getText().toString().trim();
                Log.i("GidNo", "onClickGidNo: "+GidNo);
                    presenter.getHuShenStrockPresenter(GidNo);
            }
        });
    }

    @Override
    public void getHuShenStrockResultSuccess(List<HuShenStrockResult> result) {
        List<HuShenStrockResult> results = result;
        Log.i("strockname", "getHuShenStrockResultStrockName: "+results.get(0).data.name);
    }

    @Override
    public void getHuShenStrockResultFail(String msg) {
        Log.i("strock", "getHuShenStrockResultFail: "+msg);
    }
}
