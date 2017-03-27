package com.example.melificent.myqianqi.Activity;

import android.content.Intent;
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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.melificent.myqianqi.Bean.Express.ExpressResult;
import com.example.melificent.myqianqi.Presenter.IGetExpresssInfoPresenter;
import com.example.melificent.myqianqi.Presenter.Impl.IGetExpressInfoPresenterImpl;
import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.View.GetExpressInfo;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/22.
 */

public class ExpressQueryActivity extends AppCompatActivity implements GetExpressInfo {
    @InjectView(R.id.queryexpress)
    Button query;
    @InjectView(R.id.express_querycompany)
    EditText company;
    @InjectView(R.id.express_queryNo)
    EditText No;
    @InjectView(R.id.expressgoback)
    ImageView back;
    @InjectView(R.id.express_company_more)
    ImageView company_more;
    IGetExpresssInfoPresenter presenter = new IGetExpressInfoPresenterImpl(this);
    String com;
    private PopupWindow popupWindow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expressqueryactivity);
        ButterKnife.inject(this);
        setButtonListener();
    }

    private void setButtonListener() {

        company_more.setOnClickListener(new View.OnClickListener() {
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

                ListView listView= (ListView) view.findViewById(R.id.express_listview);
                final String [] data = new String[]{
                        "顺丰","申通","圆通","韵达","天天","EMS","中通","汇通",
                        "全峰","德邦","国通","如风达","京东快递","宅急送","EMS国际",
                        "Fedex国际","邮政国内（挂号信）","UPS国际快递","中铁快运"
                };
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_list_item_1,data);

                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position){
                            case 0:
                                com = "sf";
                                company.setText(data[0]);
                                break;
                            case 1:
                                com= "sto";
                                company.setText(data[1]);
                                break;
                            case 2:
                                com = "yt";
                                company.setText(data[2]);
                                break;
                            case 3:
                                com = "yd";
                                company.setText(data[3]);
                                break;
                            case 4:
                                com = "tt";
                                company.setText(data[4]);
                                break;
                            case 5:
                                com = "ems";
                                company.setText(data[5]);
                                break;
                            case 6:
                                com = "zto";
                                company.setText(data[6]);
                                break;
                            case 7:
                                com = "ht";
                                company.setText(data[7]);
                                break;
                            case 8:
                                com = "qf";
                                company.setText(data[8]);
                                break;
                            case 9:
                                com = "db";
                                company.setText(data[9]);
                                break;
                            case 10:
                                com = "gt";
                                company.setText(data[10]);
                                break;
                            case 11:
                                com = "rfd";
                                company.setText(data[11]);
                                break;
                            case 12:
                                com = "jd";
                                company.setText(data[12]);
                                break;
                            case 13:
                                com = "zjs";
                                company.setText(data[13]);
                                break;
                            case 14:
                                com = "emsg";
                                company.setText(data[14]);
                                break;
                            case 15:
                                com = "fedex";
                                company.setText(data[15]);
                                break;
                            case 16:
                                com = "yzgn";
                                company.setText(data[16]);
                                break;
                            case 17:
                                com = "ups";
                                company.setText(data[17]);
                                break;
                            case 18:
                                com = "ztky";
                                break;

                        }
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
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (No.getText().toString().trim()!= null){
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

                    presenter.getExpressInfoPresenter(com,No.getText().toString().trim());
                }else {
                    Toast.makeText(ExpressQueryActivity.this, "快递公司或单号不能为空!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void getExpressInfoSuccess(ExpressResult result) {
        Intent intent = new Intent(this,ExpressQueryResultActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("express",result);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    @Override
    public void getExpressInfoFail(String error_Msg) {
        Toast.makeText(this, "获取物流信息失败", Toast.LENGTH_SHORT).show();
    }
}
