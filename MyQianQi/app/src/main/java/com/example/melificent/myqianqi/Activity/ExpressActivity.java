package com.example.melificent.myqianqi.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.melificent.myqianqi.Bean.Express.ExpressResult;
import com.example.melificent.myqianqi.Presenter.IGetExpresssInfoPresenter;
import com.example.melificent.myqianqi.Presenter.Impl.IGetExpressInfoPresenterImpl;
import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.View.GetExpressInfo;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/16.
 */

public class ExpressActivity extends AppCompatActivity implements GetExpressInfo {
    @InjectView(R.id.express_company)
    EditText company;
    @InjectView(R.id.express_No)
    EditText express_No;
    @InjectView(R.id.express_query)
    Button query;
    String com;
    ExpressResult result;
    IGetExpresssInfoPresenter presenter = new IGetExpressInfoPresenterImpl(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.express_activity);
        ButterKnife.inject(this);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Company = company.getText().toString().trim();
                switch (Company){
                    case "顺丰":
                        com = "sf";
                        break;
                    case "申通":
                        com= "sto";
                        break;
                    case "圆通":
                        com = "yt";
                        break;
                    case "韵达":
                        com = "yd";
                        break;
                    case "天天":
                        com = "tt";
                        break;
                    case "EMS":
                        com = "ems";
                        break;
                    case "中通":
                        com = "zto";
                        break;
                    case "汇通":
                        com = "ht";
                        break;
                    case "全峰":
                        com = "qf";
                        break;
                    case "德邦":
                        com = "db";
                        break;
                    case "国通":
                        com = "gt";
                        break;
                    case "如风达":
                        com = "rfd";
                        break;
                    case "京东快递":
                        com = "jd";
                        break;
                    case "宅急送":
                        com = "zjs";
                        break;
                    case "EMS国际":
                        com = "emsg";
                        break;
                    case "Fedex国际":
                        com = "fedex";
                        break;
                    case "邮政国内（挂号信）":
                        com = "yzgn";
                        break;
                    case "UPS国际快递":
                        com = "ups";
                        break;
                    case "中铁快运":
                        com = "ztky";
                        break;

                }
                presenter.getExpressInfoPresenter(com,express_No.getText().toString().trim());
            }
        });
    }

    @Override
    public void getExpressInfoSuccess(ExpressResult result) {
            this.result = result;
        Log.i("expressRemark", "getExpressInfoRemark: "+result.list.get(0).remark);
    }

    @Override
    public void getExpressInfoFail(String error_Msg) {
        Log.i("expressError", "getExpressInfoFail: "+error_Msg);

    }
}
